/*
package org.yu.admin.archive.util;

import org.docx4j.Docx4J;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class WordToHtmlUtil2 {

    public static String convert(InputStream inputStream, String staticPath) {
        String imageOutputPath = staticPath + "/images";

        try {
            // 加载 Word 文档
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(inputStream);

            // 修复段落样式
            fixParagraphStyles(wordMLPackage.getMainDocumentPart());

            // 修复表格中的段落样式
            fixTableStyles(wordMLPackage.getMainDocumentPart());

            // 配置 HTML 导出选项
            HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
            htmlSettings.setImageDirPath(imageOutputPath);
            htmlSettings.setImageTargetUri(imageOutputPath);
            htmlSettings.setUserBodyTail("");
            htmlSettings.setUserBodyTop("");
            htmlSettings.setOpcPackage(wordMLPackage);

            // 将 Word 转换为 HTML 并写入内存
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                Docx4J.toHTML(htmlSettings, out, Docx4J.FLAG_EXPORT_PREFER_NONXSL);
            } catch (NullPointerException e) {
                System.err.println("Warning: Missing style in document. Skipping style processing.");
                Docx4J.toHTML(htmlSettings, out, Docx4J.FLAG_EXPORT_PREFER_NONXSL);
            }

            // 使用 Jsoup 格式化内存中的 HTML 字符串
            String html = out.toString(StandardCharsets.UTF_8.name());
            Document doc = Jsoup.parse(html);

            // 这里确保在同一个 Jsoup 文档上进行所有操作
            fixHtmlStyles(doc);

            // 格式化后的 HTML 字符串
            String formattedHtml = doc.html();

            // 去除回车、换行符和多余的空白字符
            formattedHtml = formattedHtml.replaceAll("[\\r\\n]+", "");  // 去除回车和换行符
            formattedHtml = formattedHtml.replaceAll("\\s{2,}", " ");   // 替换多个空白字符为一个空格
            formattedHtml = formattedHtml.trim();  // 去除字符串两端的空白字符

            // 返回格式化后的 HTML 字符串
            return formattedHtml;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to convert DOCX to HTML", e);
        }
    }

    // 修复 HTML 样式
    public static void fixHtmlStyles(Document doc) {
        // 示例：对段落元素添加类名
        Elements paragraphs = doc.select("p");
        for (Element p : paragraphs) {
            p.addClass("word-paragraph");
        }

        // 添加表格样式
        Elements tables = doc.select("table");
        for (Element table : tables) {
            table.attr("style", "width: 100%; table-layout: auto;");
        }
    }

    // 修复段落样式
    public static void fixParagraphStyles(MainDocumentPart mainDocumentPart) {
        for (Object obj : mainDocumentPart.getContent()) {
            if (obj instanceof P) {
                P p = (P) obj;
                PPr ppr = p.getPPr();

                // 如果 PPr 为 null，则创建一个新的 PPr
                if (ppr == null) {
                    ppr = Context.getWmlObjectFactory().createPPr(); // 创建新的 PPr
                    p.setPPr(ppr);
                }

                PPrBase.PStyle pStyle = ppr.getPStyle();
                // 如果没有找到样式（pStyle 为 null），我们为它设置默认值
                if (pStyle == null) {
                    pStyle = Context.getWmlObjectFactory().createPPrBasePStyle(); // 创建新的 PStyle
                    pStyle.setVal("Normal");  // 默认设置为“正常”样式
                    ppr.setPStyle(pStyle);
                }
            }
        }
    }

    // 修复表格中的段落样式
    public static void fixTableStyles(MainDocumentPart mainDocumentPart) {
        for (Object obj : mainDocumentPart.getContent()) {
            if (obj instanceof Tbl) {
                Tbl table = (Tbl) obj;
                for (Object rowObj : table.getContent()) {
                    if (rowObj instanceof Tr) {
                        Tr row = (Tr) rowObj;
                        for (Object cellObj : row.getContent()) {
                            if (cellObj instanceof Tc) {
                                Tc cell = (Tc) cellObj;
                                for (Object cellContent : cell.getContent()) {
                                    if (cellContent instanceof P) {
                                        P p = (P) cellContent;
                                        PPr ppr = p.getPPr();
                                        if (ppr == null) {
                                            ppr = Context.getWmlObjectFactory().createPPr();
                                            p.setPPr(ppr);
                                        }
                                        if (ppr.getPStyle() == null) {
                                            PPrBase.PStyle pStyle = Context.getWmlObjectFactory().createPPrBasePStyle();
                                            pStyle.setVal("Normal");
                                            ppr.setPStyle(pStyle);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // 示例用法
        //InputStream inputStream = new FileInputStream("F:\\Git_Project\\yu-platform\\upload\\archive\\QDSHDA\\QDSHDA1-SHSW2-SHJJ\\merge.docx");
        InputStream inputStream = new FileInputStream("E:\\桌面\\3.docx");
        String html = convert(inputStream, "E:\\桌面\\Hello2.html");
        System.out.println(html);
    }
}
*/
