/*
package org.yu.admin.archive.util;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocxMerger {

    public static void main(String[] args) {
        try {
            // 指定文件夹路径、文件前缀和编号范围
            String folderPath = "E:\\桌面\\导入文件\\QDSHDA1\\";
            String prefix = "QDSHDA1-SHSW2-SHJJ_";
            int startRange = 1; // 起始编号
            int endRange = 25; // 结束编号
            String outputFile = "E:\\桌面\\3.docx";

            // 获取文件路径列表
            List<String> filePathList = generateFilePathList(folderPath, prefix, startRange, endRange);

            // 合并文档
            mergeDocx(filePathList, outputFile);
            System.out.println("文档合并成功: " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    */
/**
 * 根据前缀和范围生成文件路径列表
 *
 * @param folderPath 文件夹路径
 * @param prefix     文件前缀
 * @param startRange 起始编号
 * @param endRange   结束编号
 * @return 文件路径列表
 *//*

    public static List<String> generateFilePathList(String folderPath, String prefix, int startRange, int endRange) {
        List<String> filePathList = new ArrayList<>();
        for (int i = startRange; i <= endRange; i++) {
            String fileName = prefix + String.format("%02d", i) + ".docx"; // 格式化编号为两位数字
            filePathList.add(folderPath + fileName);
        }
        return filePathList;
    }

    */
/**
 * 合并多个 .docx 文件
 *
 * @param inputFiles 输入文件路径列表
 * @param outputFile 输出文件路径
 * @throws Docx4JException 文档处理异常
 * @throws IOException     IO 异常
 *//*

    public static void mergeDocx(List<String> inputFiles, String outputFile) throws Docx4JException, IOException {
        if (inputFiles == null || inputFiles.isEmpty()) {
            throw new IllegalArgumentException("输入文件列表不能为空");
        }

        // 加载第一个文档作为基准
        WordprocessingMLPackage mainDoc = WordprocessingMLPackage.load(new File(inputFiles.get(0)));
        MainDocumentPart mainPart = mainDoc.getMainDocumentPart();

        // 合并后续文档
        for (int i = 1; i < inputFiles.size(); i++) {
            WordprocessingMLPackage tempDoc = WordprocessingMLPackage.load(new File(inputFiles.get(i)));
            MainDocumentPart tempPart = tempDoc.getMainDocumentPart();

            // 获取临时文档的内容
            List<Object> tempContent = tempPart.getContent();

            // 将内容追加到主文档
            for (Object content : tempContent) {
                mainPart.getContent().add(content);
            }

            // 在每个文档后面添加分页符
            addPageBreak(mainPart);
        }

        // 保存合并后的文档
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            mainDoc.save(fos);
        }
    }

    */
/**
 * 在文档末尾添加分页符
 *
 * @param mainPart 主文档部分
 *//*

    private static void addPageBreak(MainDocumentPart mainPart) {
        // 创建分页符
        P paragraph = new P();
        R run = new R();
        Br breakElement = new Br();
        breakElement.setType(STBrType.PAGE); // 设置分页符类型
        run.getContent().add(breakElement);
        paragraph.getContent().add(run);

        // 将分页符添加到文档末尾
        mainPart.getContent().add(paragraph);
    }
}*/
