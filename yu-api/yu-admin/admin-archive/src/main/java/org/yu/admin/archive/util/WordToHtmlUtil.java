package org.yu.admin.archive.util;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.core.FileURIResolver;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.serve.file.util.FilePathUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WordToHtmlUtil {

    public static String getContextPath() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest().getContextPath();
    }

    public static String convert(InputStream inputStream, String staticPath) {

        String imageOutputPath = staticPath + File.separator + "images";

        try (XWPFDocument document = new XWPFDocument(inputStream);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // 配置 HTML 导出选项
            XHTMLOptions options = XHTMLOptions.create().indent(4);

            File imageFolder = FilePathUtil.getFileByStaticPath(imageOutputPath);
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }

            options.setExtractor(new FileImageExtractor(imageFolder));
            options.URIResolver(new FileURIResolver(imageFolder) {
                @Override
                public String resolve(String uri) {
                    // 修改图片路径为相对路径，例如：images/word/media/image1.png
                    return (getContextPath() + File.separator + FilePathUtil.getRoot() + staticPath + "/images/word/media/" + uri.substring(uri.lastIndexOf("/") + 1)).replace("\\", "/");
                }
            });

            // 将 Word 转换为 HTML 并写入内存
            // 捕获转换过程中的异常
            try {
                // 将 Word 转换为 HTML 并写入内存
                XHTMLConverter.getInstance().convert(document, out, options);
            } catch (Exception e) {
                // 记录日志并返回错误信息
                log.error("Failed to convert DOCX to HTML", e);
                return "Failed to convert DOCX to HTML: " + e.getMessage();
            }

            // 使用 Jsoup 格式化内存中的 HTML 字符串
            String html = out.toString("UTF-8")
                    .replaceAll("Evaluation Only. Created with Aspose.Words. Copyright 2003-2022 Aspose Pty Ltd.", "")
                    .replaceAll("This document was truncated here because it was created in the Evaluation Mode.", "");
            System.out.println("html: " + html);
            Document doc = Jsoup.parse(html);

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

            // 格式化后的 HTML 字符串
            String formattedHtml = doc.html();

            // 去除回车、换行符和多余的空白字符
            formattedHtml = formattedHtml.replaceAll("[\\r\\n]+", "");  // 去除回车和换行符
            formattedHtml = formattedHtml.replaceAll("\\s{2,}", " ");   // 替换多个空白字符为一个空格
            formattedHtml = formattedHtml.trim();  // 去除字符串两端的空白字符

            // 将字符串写入文件 TODO 后续删除
            /*File htmlFile = new File(filePath + File.separator + "output.html");
            try (FileOutputStream fos = new FileOutputStream(htmlFile)) {
                fos.write(formattedHtml.getBytes(Charset.forName("UTF-8")));
            }*/

            // 返回格式化后的 HTML 字符串
            return HtmlToWangEditorFormat.convertHtmlToWangEditorFormat(formattedHtml);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*public static void convert(String wordFilePath, String htmlOutputPath) {
        try (XWPFDocument document = new XWPFDocument(new FileInputStream(wordFilePath))) {
            OutputStream out = new FileOutputStream(htmlOutputPath);

            XHTMLOptions options = XHTMLOptions.create().indent(4);
            File imageFolder = new File("images");
            if (!imageFolder.exists()) {
                imageFolder.mkdirs();
            }
            options.setExtractor(new FileImageExtractor(imageFolder));
            options.URIResolver(new FileURIResolver(imageFolder));
            XHTMLConverter.getInstance().convert(document, out, options);

            // 使用Jsoup读取生成的HTML文件进行格式化修复
            File htmlFile = new File(htmlOutputPath);
            Document doc = Jsoup.parse(htmlFile, String.valueOf(Charset.forName("UTF-8")), "");
            // 示例：对段落元素进行格式优化，添加类名等（可按需扩展更多元素处理逻辑）
            Elements paragraphs = doc.select("p");
            for (Element p : paragraphs) {
                p.addClass("word-paragraph");
            }
            // 整理HTML内容结构，缩进等格式调整
            String formattedHtml = doc.html();
            formattedHtml = formatHtml(formattedHtml);

            // 将格式化后的HTML重新写入文件
            FileOutputStream fos = new FileOutputStream(htmlOutputPath);
            fos.write(formattedHtml.getBytes(Charset.forName("UTF-8")));
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private static String formatHtml(String html) {
        Document doc = Jsoup.parse(html);
        List<Node> nodes = new ArrayList<>(doc.childNodes()); // 创建一个新的可修改的节点列表
        for (Node node : nodes) {
            if (node instanceof TextNode) {
                TextNode textNode = (TextNode) node;
                String text = textNode.text().trim();
                if (text.isEmpty()) {
                    doc.body().removeClass(String.valueOf(node)); // 从文档中移除空的文本节点
                }
            }
        }

        // 查找所有表格元素，并添加自适应宽度的样式
        Elements tables = doc.select("table");
        for (Element table : tables) {
            table.attr("style", "width: 100%; table-layout: auto;");
        }
        return doc.html(); // 返回修改后的 HTML 字符串
    }

    public static void main(String[] args) throws FileNotFoundException {
        //convert("E:\\桌面\\Hello.docx", "E:\\桌面\\Hello.html");
        //InputStream inputStream = new FileInputStream("F:\\Git_Project\\yu-platform\\upload\\archive\\QDSHDA\\QDSHDA1-SHSW2-SHJJ\\merge.docx");
        InputStream inputStream = new FileInputStream("E:\\桌面\\3.docx");
        String html = convert(inputStream, "E:\\桌面\\Hello2.html");
        System.out.println(html);
        // File path where the content will be written
        File file = new File("E:\\桌面\\Hello2.html");

        // Writing the content to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(html
                    .replaceAll("<p>Evaluation Only. Created with Aspose.Words. Copyright 2003-2022 Aspose Pty Ltd.</p>", "")
                    .replaceAll("<p>This document was truncated here because it was created in the Evaluation Mode.</p>", "")
            );
            System.out.println("Content written to file successfully!");
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}