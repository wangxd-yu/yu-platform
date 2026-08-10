package org.yu.admin.archive.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlToDeltaConverter {

    // 将 HTML 转换为 Delta 格式
    public static JSONObject convertHtmlToDelta(String html) {
        // 解析 HTML 文档
        Document doc = Jsoup.parse(html);

        // Delta 的 root
        JSONObject delta = new JSONObject();
        JSONArray ops = new JSONArray();

        // 处理每一个段落、标题等
        Elements paragraphs = doc.select("p, h1, h2, h3, h4, h5, h6, ul, ol, li, img, a");

        for (Element element : paragraphs) {
            JSONObject op = new JSONObject();

            if (element.tagName().equals("p")) {
                op.put("insert", element.text() + "\n");
            } else if (element.tagName().matches("h[1-6]")) {
                op.put("insert", element.text() + "\n");
                op.put("attributes", new JSONObject().put("header", Integer.parseInt(element.tagName().substring(1))));
            } else if (element.tagName().equals("ul")) {
                Elements listItems = element.select("li");
                for (Element li : listItems) {
                    JSONObject listOp = new JSONObject();
                    listOp.put("insert", li.text() + "\n");
                    listOp.put("attributes", new JSONObject().put("list", "bullet"));
                    ops.add(listOp);
                }
            } else if (element.tagName().equals("ol")) {
                Elements listItems = element.select("li");
                for (Element li : listItems) {
                    JSONObject listOp = new JSONObject();
                    listOp.put("insert", li.text() + "\n");
                    listOp.put("attributes", new JSONObject().put("list", "ordered"));
                    ops.add(listOp);
                }
            } else if (element.tagName().equals("img")) {
                op.put("insert", new JSONObject().put("image", element.attr("src")));
            } else if (element.tagName().equals("a")) {
                op.put("insert", element.text());
                op.put("attributes", new JSONObject().put("link", element.attr("href")));
            }

            ops.add(op);
        }

        delta.put("ops", ops);
        return delta;
    }

    public static void main(String[] args) {
        String htmlContent = "<h1>Title</h1><p>This is a paragraph.</p><ul><li>Item 1</li><li>Item 2</li></ul><img src='image.jpg'/>";

        // Convert HTML to Delta
        JSONObject delta = convertHtmlToDelta(htmlContent);
        System.out.println(delta.toString());
    }
}
