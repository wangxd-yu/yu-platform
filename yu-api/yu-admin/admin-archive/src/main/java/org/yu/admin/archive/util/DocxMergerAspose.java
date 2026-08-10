package org.yu.admin.archive.util;

import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DocxMergerAspose {

    public static void main(String[] args) {
        try {
            // 指定文件夹路径、文件前缀和编号范围
            String folderPath = "E:\\桌面\\导入文件\\QDSHDA1\\";
            String prefix = "QDSHDA1-SHSW2-SHJJ_";
            int startRange = 0; // 起始编号
            int endRange = 80; // 结束编号
            String outputFile = "E:\\桌面\\3.docx";

            // 获取文件路径列表
            List<String> filePathList = generateFilePathList(folderPath, prefix, startRange, endRange);

            // 合并文档
            Document output = new Document();
            // 在追加之前从目标文档中删除所有内容。
            output.removeAllChildren();

            for (String fileName : filePathList) {
                Document input = new Document(fileName);
                // 将源文档附加到目标文档的末尾。
                output.appendDocument(input, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            }

            output.save(outputFile);
            System.out.println("文档合并成功: " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据前缀和范围生成文件路径列表
     *
     * @param folderPath 文件夹路径
     * @param prefix     文件前缀
     * @param startRange 起始编号
     * @param endRange   结束编号
     * @return 文件路径列表
     */
    public static List<String> generateFilePathList(String folderPath, String prefix, int startRange, int endRange) {
        List<String> filePathList = new ArrayList<>();
        for (int i = startRange; i <= endRange; i++) {
            String fileName = prefix + String.format("%02d", i) + ".docx"; // 格式化编号为两位数字
            filePathList.add(folderPath + fileName);
        }
        return filePathList;
    }

    public static void mergeDoc(String mergeFilePath, ZipFile zip, List<ZipEntry> collect) throws Exception {
        // 合并文档
        Document output = new Document();
        // 在追加之前从目标文档中删除所有内容。
        output.removeAllChildren();

        for (ZipEntry zipEntry : collect) {
            InputStream in = null;
            try {
                in = zip.getInputStream(zipEntry);
                Document input = new Document(in);
                // 将源文档附加到目标文档的末尾。
                output.appendDocument(input, ImportFormatMode.KEEP_SOURCE_FORMATTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        output.save(mergeFilePath);
    }
}