package org.yu.admin.archive.module.archive.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.yu.admin.archive.module.archive.domain.BaseArchiveDO;
import org.yu.admin.archive.module.archive.domain.DataJjtldaDO;
import org.yu.admin.archive.module.archive.domain.DataQdshdaDO;
import org.yu.admin.archive.module.archive.domain.DataSdgmlsDO;
import org.yu.admin.archive.module.archive.query.DataJjtldaQuery;
import org.yu.admin.archive.module.archive.query.DataQdshdaQuery;
import org.yu.admin.archive.module.archive.query.DataSdgmlsQuery;
import org.yu.admin.archive.module.archive.repository.BaseArchiveRepository;
import org.yu.admin.archive.module.archive.repository.DataJjtldaRepository;
import org.yu.admin.archive.module.archive.repository.DataQdshdaRepository;
import org.yu.admin.archive.module.archive.repository.DataSdgmlsRepository;
import org.yu.admin.archive.util.DocxMergerAspose;
import org.yu.admin.archive.util.ExcelValidator;
import org.yu.admin.archive.util.UnZipUtils;
import org.yu.admin.archive.util.WordToHtmlUtil;
import org.yu.common.core.exception.ServiceException;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.query.util.WrapDataUtil;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.serve.file.util.FilePathUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author wangxd
 * @date 2024-12-21 16:02
 */
@Log4j2
@Service
public class ArchiveDataServiceImpl implements ArchiveDataService {

    static Map<String, List<String>> EXCEL_TITLE_MAP = new HashMap<String, List<String>>() {{
        // 使用双括号初始化
        put("SDGMLS", Arrays.asList(
                "数据库名称", "文献系列名称*", "文献名称*", "文件序号", "文件题名*", "文件时间*", "起迄范围", "起始文件序号", "终止文件序号", "关键词*", "人物*", "地名*", "机构*", "备注*", "其他1", "其他2", "其他3", "其他4", "其他5"
        ));
        put("QDSHDA", Arrays.asList(
                "数据库名称", "文献系列名称*", "文献名称*", "文件序号", "文件题名*", "文件时间*", "起迄范围", "起始文件序号", "终止文件序号", "关键词*", "人物*", "地名*", "机构*", "备注*", "其他1", "其他2", "其他3", "其他4", "其他5"
        ));
        put("JJTLDA", Arrays.asList(
                "資料庫名稱", "文獻系列名稱*", "文獻名稱*", "文檔序號", "文檔題名*", "文檔時間*", "起迄範圍", "起始文檔序號", "終止文檔序號", "關鍵字*", "人物*", "地名*", "機構*", "備註*", "其他1", "其他2", "其他3", "其他4", "其他5"
        ));
        // 可以继续添加更多的键值对
        put("Sheet2", new ArrayList<String>() {{
            add("Header1");
            add("Header2");
            add("Header3");
        }});
    }};
    final DataSdgmlsRepository dataSdgmlsRepository;
    final DataQdshdaRepository dataQdshdaRepository;
    final DataJjtldaRepository dataJjtldaRepository;
    @PersistenceContext
    protected EntityManager entityManager;
    String BASE_PATH = null;
    String BASE_STATIC_PATH = null;
    Map<String, DataControlMap> dataControlMap = new HashMap<>();
    @Resource
    private FilePathUtil filePathUtil;
    public ArchiveDataServiceImpl(DataSdgmlsRepository dataSdgmlsRepository, DataQdshdaRepository dataQdshdaRepository, DataJjtldaRepository dataJjtldaRepository) {
        this.dataSdgmlsRepository = dataSdgmlsRepository;
        this.dataQdshdaRepository = dataQdshdaRepository;
        this.dataJjtldaRepository = dataJjtldaRepository;
        dataControlMap.put("SDGMLS", new DataControlMap(dataSdgmlsRepository, DataSdgmlsQuery.class, DataSdgmlsDO.class));
        dataControlMap.put("QDSHDA", new DataControlMap(dataQdshdaRepository, DataQdshdaQuery.class, DataQdshdaDO.class));
        dataControlMap.put("JJTLDA", new DataControlMap(dataJjtldaRepository, DataJjtldaQuery.class, DataJjtldaDO.class));
    }

    private static void handlerWordFile(BaseArchiveDO item, InputStream wordInputStream, String staticPath, String absPath, String accessPath) throws IOException {
        byte[] wordData = IOUtils.toByteArray(wordInputStream);
        InputStream wordDataInputStreamForReuse = new ByteArrayInputStream(wordData);
        String formartHtml = WordToHtmlUtil.convert(wordDataInputStreamForReuse, staticPath);
        item.setHtml(formartHtml);
        item.setHtmlNoTag(HtmlUtil.cleanHtmlTag(formartHtml));

        // 存储word文档
        //InputStream wordDataInputStreamForStorage = new ByteArrayInputStream(wordData);
        //FileUtil.writeFromStream(wordDataInputStreamForStorage, absPath + File.separator + item.getTitle() + ".docx");
        //todo word文件地址
        item.setWord((accessPath + File.separator + item.getTitle() + ".docx").replace("\\", "/"));
    }

    public static String getContextPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getContextPath();
    }

    @PostConstruct
    private void init() {
        BASE_PATH = filePathUtil.getUploadPath() + "archive";
        BASE_STATIC_PATH = File.separator + "archive";
    }

    @Override
    public MultiDataResult<Object> query(String databaseType, Object query, Pageable pageable) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        if (dataControlMapEntry == null) {
            throw new IllegalArgumentException("No query class found for database type: " + databaseType);
        }

        // 将 query 转换为对应的 queryClass 类型
        Object typedQuery = convertObjectToType(query, dataControlMapEntry.getQueryClass());

        // 使用转换后的 query 创建 JPAQuery
        JPAQuery jpaQuery = YuQueryHelp.getJPAQuery(jpaQueryFactory, typedQuery);
        return WrapDataUtil.toPage(jpaQuery, pageable);
    }

    @Override
    public String uploadZip(String databaseType, MultipartFile multipartFile) {


        File file = UnZipUtils.getFile(multipartFile);
        if (Objects.isNull(file)) {
            return "空文件";
        }

        if (Objects.isNull(file) || !file.exists()) {
            throw new ServiceException("zip文件为空文件，请检查！");
        }

        ZipFile zip = null;
        try {
            zip = new ZipFile(file, Charset.forName("GBK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Objects.isNull(zip)) {
            throw new ServiceException("zip文件为空文件，请检查！");
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();
        List<ZipEntry> zipEntryList = new ArrayList<>();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            zipEntryList.add(entry);
        }

        // 过滤出Excel文件
        List<ZipEntry> zipEntries = zipEntryList.stream().filter(zipEntry -> zipEntry.getName().endsWith(".xlsx")).collect(Collectors.toList());
        if (zipEntries.size() > 1) {
            throw new ServiceException("存在多个Excel文件，请检查！");
        } else if (zipEntries.size() == 0) {
            throw new ServiceException("不存在Excel文件，请检查！");
        }

        ZipEntry zipEntryExcel = zipEntries.get(0);

        // 动态验证Excel的标题是否合法
        try (InputStream in = zip.getInputStream(zipEntryExcel)) { // 使用 try-with-resources 自动关闭流
            byte[] data = IOUtils.toByteArray(in);
            InputStream inputStreamForReuse = new ByteArrayInputStream(data);

            DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
            if (dataControlMapEntry == null) {
                throw new ServiceException("未找到数据库类型对应的控制映射！");
            }
            BaseArchiveRepository repository = dataControlMapEntry.getRepository();

            // 动态获取标题验证规则
            if (!ExcelValidator.validateHeaders(inputStreamForReuse, EXCEL_TITLE_MAP.get(databaseType))) {
                log.warn("文件 {} 的模板不符合预期，已跳过！", zipEntryExcel.getName());
                throw new ServiceException(String.format("Excel 文件 %s 的模板不匹配，请检查！", zipEntryExcel.getName()));
            }

            // 重新创建一个流用于读取 Excel 数据
            try (InputStream inputStreamForExcel = new ByteArrayInputStream(data)) { // 使用 try-with-resources 自动关闭流
                // 使用反射动态创建 List
                List<BaseArchiveDO> dataList = new ArrayList<>();
                EasyExcel.read(inputStreamForExcel, dataControlMapEntry.getDomainClass(), new PageReadListener<BaseArchiveDO>(dl -> {
                    log.info("读取到一条数据{}", JSONUtil.toJsonStr(dl));
                    dataList.addAll(dl);
                })).sheet().doRead();

                //过滤数据
                //List<BaseArchiveDO> dataListFilter = filterData(dataList, repository);
                for (BaseArchiveDO item : dataList) {
                    // 使用反射获取对应的字段，判断是否存在重复记录
                    try {

                        String title = item.getTitle();
                        // 在这里使用 repository 查找是否存在重复记录
                        Optional<Object> byTitleIs = repository.findByTitleIs(title);
                        if (byTitleIs.isPresent()) {
                            throw new ServiceException("存在重复记录，请检查！");
                        }
                    } catch (Exception e) {
                        throw new RuntimeException("检查重复记录时出错", e);
                    }

                    // 文件存储逻辑，继续处理 Word、PDF 等文件
                    String tempPath = databaseType + File.separator + item.getFileNumber();
                    //绝对路径
                    String absPath = BASE_PATH + File.separator + tempPath;
                    FileUtil.mkParentDirs(absPath);

                    //相对路径
                    String staticPath = BASE_STATIC_PATH + File.separator + databaseType + File.separator + item.getFileNumber();
                    //访问路径
                    String accessPath = getContextPath() + File.separator + FilePathUtil.getRoot() + staticPath;

                    //合并word文件
                    String mergeFilePath = absPath + File.separator + "merge.docx";
                    File mergeFile = new File(mergeFilePath);

                    // 如果文件不存在，则创建文件
                    if (!mergeFile.exists()) {
                        try {
                            mergeFile.getParentFile().mkdirs(); // 确保父目录存在
                            mergeFile.createNewFile();
                        } catch (IOException e) {
                            throw new RuntimeException("创建文件失败: " + mergeFilePath, e);
                        }
                    }
                    try {
                        // 将 word 转换为 html，存储 word 内部的图片文档
                        List<ZipEntry> collect = zipEntryList.stream()
                                .filter(zipEntry -> zipEntry.getName().startsWith(item.getFileNumber()) && zipEntry.getName().endsWith(".docx"))
                                .collect(Collectors.toList());
                        if (!collect.isEmpty()) {
                            //MagerUtil.mergeDoc(new FileOutputStream(mergeFilePath), zip, collect);
                            DocxMergerAspose.mergeDoc(mergeFilePath, zip, collect);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    try (InputStream wordInputStream = new FileInputStream(mergeFilePath)) { // 使用 try-with-resources 自动关闭流
                        handlerWordFile(item, wordInputStream, staticPath, absPath, accessPath);
                    }

                    /*try {
                        // 将 word 转换为 html，存储 word 内部的图片文档
                        ZipEntry zipEntryWord = zipEntryList.stream().filter(zipEntry -> zipEntry.getName().equals(item.getTitle() + ".docx")).findFirst().orElse(null);
                        if (zipEntryWord != null) {
                            try (InputStream wordInputStream = zip.getInputStream(zipEntryWord)) { // 使用 try-with-resources 自动关闭流
                                handlerWordFile(item, wordInputStream, staticPath, absPath, accessPath);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }*/

                    // 继续存储PDF文档
                    String prefix = item.getFileNumber();
                    //String patternStr = "^" + Pattern.quote(prefix) + ".*\\.pdf$";
                    String patternStr = "^" + Pattern.quote(prefix) + ".*\\.(pdf|jpg|jpeg|png)$";
                    Pattern pattern = Pattern.compile(patternStr);

                    List<ZipEntry> filteredEntries = zipEntryList.stream()
                            .filter(zipEntry -> pattern.matcher(zipEntry.getName()).matches())
                            .collect(Collectors.toList());


                    String finalFilePath = absPath;
                    List pdfTag = new ArrayList();
                    List<String> pdfPathList = new ArrayList();
                    for (ZipEntry zipEntry : filteredEntries) {
                        try (InputStream pdfInputStream = zip.getInputStream(zipEntry)) { // 使用 try-with-resources 自动关闭流
                            File outputFile = new File(finalFilePath, zipEntry.getName());
                            FileUtil.writeFromStream(pdfInputStream, outputFile);
                            pdfTag.add(zipEntry.getName()
                                    .replaceAll(prefix + "-", "")
                                    .replaceAll("\\.(pdf|jpg|jpeg|png)$", ""));
                            pdfPathList.add((accessPath + File.separator + zipEntry.getName()).replace("\\", "/"));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    pdfPathList = pdfPathList.stream().sorted().collect(Collectors.toList());
                    item.setPdfs(JSONUtil.toJsonStr(pdfPathList));
                    //item.setPdfs(String.join(",", pdfTag));
                }

                repository.saveAll(dataList);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (zip != null) {
            try {
                zip.close();
            } catch (IOException e) {
                log.error("关闭ZipFile时发生错误", e);
            }
        }

        // 删除临时文件
        if (file.exists()) {
            boolean deleteSuccessful = deleteFileWithRetry(file);
            if (!deleteSuccessful) {
                System.out.println(file.getName() + " 临时文件删除失败！");
            }
        }
        return "";
    }

    @Override
    public Object getById(String databaseType, String id) {
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        if (dataControlMapEntry == null) {
            throw new ServiceException("未找到数据库类型对应的控制映射！");
        }
        BaseArchiveRepository repository = dataControlMapEntry.getRepository();
        return repository.findById(id).get();
    }

    @Override
    public void updateData(String databaseType, Object domain) {
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        BaseArchiveRepository repository = dataControlMapEntry.getRepository();
        // 将 query 转换为对应的 queryClass 类型
        Object typedQuery = convertObjectToType(domain, dataControlMapEntry.getDomainClass());
        repository.save(typedQuery);
    }

    @Override
    public void deleteById(String databaseType, String id) {
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        BaseArchiveRepository repository = dataControlMapEntry.getRepository();
        repository.deleteById(id);
    }

    @Override
    public void create(String databaseType, String domain, MultipartFile wordFile, List<MultipartFile> pdfFiles) {
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        BaseArchiveRepository repository = dataControlMapEntry.getRepository();
        BaseArchiveDO baseArchiveDO = (BaseArchiveDO) convertJsonStringToObject(domain, dataControlMapEntry.getDomainClass());
        // 在这里使用 repository 查找是否存在重复记录
        Optional<Object> byTitleIs = repository.findByTitleIs(baseArchiveDO.getTitle());
        if (byTitleIs.isPresent()) {
            throw new ServiceException("存在重复记录，请检查！");
        }


        // 文件存储逻辑，继续处理 Word、PDF 等文件
        String tempPath = databaseType + File.separator + baseArchiveDO.getFileNumber();
        //绝对路径
        String absPath = BASE_PATH + File.separator + tempPath;
        FileUtil.mkParentDirs(absPath);

        //相对路径
        String staticPath = BASE_STATIC_PATH + File.separator + tempPath;
        //访问路径
        String accessPath = getContextPath() + File.separator + FilePathUtil.getRoot() + staticPath;

        try {
            handlerWordFile(baseArchiveDO, wordFile.getInputStream(), staticPath, absPath, accessPath);

            List<String> pdfPathList = new ArrayList();
            for (MultipartFile pdfFile : pdfFiles) {
                File outputFile = new File(absPath, pdfFile.getOriginalFilename());
                FileUtil.writeFromStream(pdfFile.getInputStream(), outputFile);
                pdfPathList.add((accessPath + File.separator + pdfFile.getOriginalFilename()).replace("\\", "/"));
            }
            baseArchiveDO.setPdfs(JSONUtil.toJsonStr(pdfPathList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        repository.save(baseArchiveDO);
    }

    @Override
    public void updateHtml(String databaseType, String id, String html) {
        DataControlMap dataControlMapEntry = dataControlMap.get(databaseType);
        BaseArchiveRepository repository = dataControlMapEntry.getRepository();
        BaseArchiveDO baseArchiveDO = (BaseArchiveDO) repository.getById(id);
        baseArchiveDO.setHtml(html);
        repository.save(baseArchiveDO);
    }

    private boolean deleteFileWithRetry(File file) {
        if (file != null && file.exists()) {
            Path path = Paths.get(file.getAbsolutePath());
            try {
                Files.delete(path); // 使用 Files.delete() 来删除文件
                log.info("文件删除成功，文件路径: {}", file.getAbsolutePath());
            } catch (java.nio.file.NoSuchFileException e) {
                log.error("删除失败: 文件不存在，文件路径: {}", file.getAbsolutePath(), e);
            } catch (java.nio.file.DirectoryNotEmptyException e) {
                log.error("删除失败: 目录非空，文件路径: {}", file.getAbsolutePath(), e);
            } catch (java.io.IOException e) {
                log.error("删除失败: I/O 错误，文件路径: {}", file.getAbsolutePath(), e);
            } catch (Exception e) {
                log.error("删除失败: 未知错误，文件路径: {}", file.getAbsolutePath(), e);
            }
        } else {
            log.error("删除失败: 文件不存在，文件路径: {}", file != null ? file.getAbsolutePath() : "null");
        }
        return true;
    }


    private List<BaseArchiveDO> filterData(List<BaseArchiveDO> dataList, BaseArchiveRepository repository) {
        List<BaseArchiveDO> filteredDataList = new ArrayList<>();
        dataList.forEach(item -> {
            String title = item.getTitle();
            // 在这里使用 repository 查找是否存在重复记录
            Optional<Object> byTitleIs = repository.findByTitleIs(title);
            if (!byTitleIs.isPresent()) {
                filteredDataList.add(item);
            }
        });
        return filteredDataList;
    }

    private Object convertObjectToType(Object object, Class<?> targetType) {
        try {
            // 使用 Jackson 的 ObjectMapper 进行自动映射
            ObjectMapper objectMapper = new ObjectMapper();
            // 配置 ObjectMapper 忽略多余的字段
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            return objectMapper.convertValue(object, targetType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert query to type: " + targetType.getName(), e);
        }
    }

    private Object convertJsonStringToObject(String jsonString, Class<?> targetType) {
        try {
            // 使用 Jackson 的 ObjectMapper 进行自动映射
            ObjectMapper objectMapper = new ObjectMapper();
            // 配置 ObjectMapper 忽略多余的字段
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 将 JSON 字符串转换为目标类型的对象
            return objectMapper.readValue(jsonString, targetType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to convert JSON string to type: " + targetType.getName(), e);
        }
    }
}

@Data
@AllArgsConstructor
class DataControlMap {
    BaseArchiveRepository repository;
    Class queryClass;
    Class domainClass;
}
