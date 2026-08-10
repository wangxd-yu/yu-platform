package org.yu.admin.archive.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.yu.admin.archive.module.archive.domain.DataSdgmlsDO;
import org.yu.common.core.exception.ServiceException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 解析文件工具类
 *
 * @author Yuanqiang.Zhang
 * @since 2022/7/12
 */
@SuppressWarnings("unused")
@Log4j2
public class UnZipUtils {

    // 预期标题列表
    private static final List<String> EXPECTED_HEADERS = Arrays.asList(
            "数据库名称", "文件序号", "题名", "文件时间", "责任者",
            "受文者", "主题词1", "主题词2", "人物", "地名",
            "机构", "会议", "文件类型", "文件来源", "档号", "备注",
            "其他1", "其他2", "其他3", "其他4", "其他5"
    );
    public static int BYTE_LEN = 10240;
    public static String BASE_PATH = System.getProperty("user.dir") + File.separator + "SDGMLS";

    /**
     * 本地文件解压
     *
     * @param file 具体文件
     * @return 解压后的文件列表
     */
    public static List<ZipEntry> unzipToZipEntry(File file) {
        if (Objects.isNull(file) || !file.exists()) {
            return Collections.emptyList();
        }
        ZipFile zip = null;
        try {
            // 指定字符编码，例如 "GBK" 或 "UTF-8"
            zip = new ZipFile(file, Charset.forName("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(zip)) {
            return Collections.emptyList();
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();
        List<ZipEntry> zipEntryList = new ArrayList<>();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            zipEntryList.add(entry);
        }
        return zipEntryList;
    }

    /**
     * 本地文件解压
     *
     * @param file 具体文件
     * @return 解压后的文件列表
     */
    public static List<UnzipFileVo> unzip(File file) {
        if (Objects.isNull(file) || !file.exists()) {
            return Collections.emptyList();
        }
        ZipFile zip = null;
        try {
            // 指定字符编码，例如 "GBK" 或 "UTF-8"
            zip = new ZipFile(file, Charset.forName("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(zip)) {
            return Collections.emptyList();
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();
        List<ZipEntry> zipEntryList = new ArrayList<>();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            zipEntryList.add(entry);
        }

        List<ZipEntry> zipEntries = zipEntryList.stream().filter(zipEntry -> zipEntry.getName().endsWith(".xlsx")).collect(Collectors.toList());
        if (zipEntries.size() > 1) {
            throw new ServiceException("存在多个Excel文件，将检查！");
        } else if (zipEntries.size() == 0) {
            throw new ServiceException("不存在Excel文件，将检查！");
        }

        ZipEntry zipEntryExcel = zipEntries.get(0);

        // 验证标题
        try (InputStream in = zip.getInputStream(zipEntryExcel)) {
            byte[] data = IOUtils.toByteArray(in);
            InputStream inputStreamForReuse = new ByteArrayInputStream(data);

            if (!ExcelValidator.validateHeaders(inputStreamForReuse, EXPECTED_HEADERS)) {
                log.warn("文件 {} 的模板不符合预期，已跳过！", zipEntryExcel.getName());
                throw new ServiceException(String.format("Excel 文件 {} 的模板不匹配，请检查！", zipEntryExcel.getName()));
            }

            List<DataSdgmlsDO> dataList = new ArrayList<>();
            // 重新创建一个流用于读取 Excel 数据
            InputStream inputStreamForExcel = new ByteArrayInputStream(data);
            //1、解析 excel，获取文件列表。同一个文件所属文件统一放到一个文件夹中，目录为：文件序号
            EasyExcel.read(inputStreamForExcel, DataSdgmlsDO.class, new PageReadListener<DataSdgmlsDO>(dl -> {
                log.info("读取到一条数据{}", JSONUtil.toJsonStr(dl));
                dataList.addAll(dl);
            })).sheet().doRead();

            ZipFile finalZip = zip;
            dataList.forEach(item -> {
                // 指定文件目录：使用 “文件序号”做目录
                String filePath = BASE_PATH + File.separator + item.getFileNumber();
                //2、将word转换为 html，存储word内部的image文档
                try {
                    ZipEntry zipEntryWord = zipEntryList.stream().filter(zipEntry -> zipEntry.getName().equals(item.getTitle() + ".docx")).findFirst().orElse(null);
                    InputStream wordInputStream = finalZip.getInputStream(zipEntryWord);

                    byte[] wordData = IOUtils.toByteArray(wordInputStream);

                    InputStream wordDataInputStreamForReuse = new ByteArrayInputStream(wordData);
                    WordToHtmlUtil.convert(wordDataInputStreamForReuse, filePath);

                    InputStream wordDataInputStreamForStorage = new ByteArrayInputStream(wordData);
                    //3、存储word文档
                    FileUtil.writeFromStream(wordDataInputStreamForStorage, filePath + File.separator + item.getTitle() + ".docx");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //4、存储pdf文档，解析pdf页码
                //文件名前缀 item.getTitle()
                String prefix = item.getTitle(); // 替换为你的实际前缀
                String patternStr = "^" + Pattern.quote(prefix) + ".*\\.pdf$";
                Pattern pattern = Pattern.compile(patternStr);

                List<ZipEntry> filteredEntries = zipEntryList.stream()
                        .filter(zipEntry -> pattern.matcher(zipEntry.getName()).matches())
                        .collect(Collectors.toList());

                filteredEntries.forEach(zipEntry -> {
                    try {
                        System.out.println("----------------------  " + zipEntry.getName().replaceAll(item.getTitle() + "-", "").replaceAll(".pdf", ""));  // 返回捕获组1的内容，即前缀和后缀之间的文本
                        InputStream wordInputStream = finalZip.getInputStream(zipEntry);
                        // 存储匹配的文件到指定文件夹
                        File outputFile = new File(filePath, zipEntry.getName());
                        FileUtil.writeFromStream(wordInputStream, outputFile);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

            });

        } catch (IOException e) {
            e.printStackTrace();
            // 跳过损坏的文件条目
        }
        return null;
    }

    public static void main(String args[]) {
        File file = new File("E:\\桌面\\导入文件\\导入文件.zip");
        System.out.println(unzip(file));
    }


    /**
     * 上传文件解压
     *
     * @param multipartFile 上传文件
     * @return 解压后的文件列表
     */
   /* public static List<UnzipFileVo> unzip(MultipartFile multipartFile) {
        File file = getFile(multipartFile);
        if (Objects.isNull(file)) {
            return Collections.emptyList();
        }
        List<UnzipFileVo> vos = unzip(file);
        if (file.exists()) {
            boolean delete = file.delete();
            if (delete) {
                System.out.println(file.getName() + " 临时文件删除成功！");
            } else {
                System.out.println(file.getName() + " 临时文件删除失败！");
            }
        }
        return vos;
    }*/

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile 上传文件
     * @return 本地文件
     */
    public static File getFile(MultipartFile multipartFile) {
        String fileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        File file = new File(BASE_PATH + File.separator + fileName);
        int len;
        try (OutputStream os = new FileOutputStream(file);
             InputStream in = multipartFile.getInputStream()) {
            byte[] buffer = new byte[BYTE_LEN];
            while ((len = in.read(buffer, 0, BYTE_LEN)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}