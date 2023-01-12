package com.destiny.squirrel.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author destiny
 * @Date 2021-07-07 10:51 AM
 */
public class HtmlReadUtils {

    private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?<\\/script>";        /* 定义script的正则表达式 */
    private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?<\\/style>";          /* 定义style的正则表达式 */
    private static final String REG_EX_HTML = "<[^>]+>";                                    /* 定义HTML标签的正则表达式<[^>]*> */
    private static final String REG_EX_SPACE = "<a>\\s*|\t|\r|\n</a>";                       /* 定义空格回车换行符 */
    private static final String REG_EX_SPAN = "/<span[^>]*>([^<]*)<\\/span>/ig";                       /*  span*/
    private static final String REG_EX_STRONG = "<strong.*?>(((?!<\\/strong>).)*)<\\/strong>";                       /* strong*/


    public static String delHTMLTag(String htmlStr) {
        /* 去掉script标签 */
        Pattern p_script = Pattern.compile(REG_EX_SCRIPT,
                Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll("");    /* 过滤script标签 */
        /* 去掉style标签 */
        Pattern p_style = Pattern
                .compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll("");     /* 过滤style标签 */
        /* 去掉html标签 */
        Pattern p_html = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll("");      /* 过滤html标签 */
        /* 去掉span标签 */
        Pattern span_html = Pattern.compile(REG_EX_SPAN, Pattern.CASE_INSENSITIVE);
        Matcher span = span_html.matcher(htmlStr);
        htmlStr = span.replaceAll("");      /* 过滤html标签 */
        /* 去掉regEx_strong标签 */
        Pattern strong_html = Pattern.compile(REG_EX_STRONG, Pattern.CASE_INSENSITIVE);
        Matcher strong = strong_html.matcher(htmlStr);
        htmlStr = strong.replaceAll("");      /* 过滤html标签 */
        /* 去掉空格 */
        Pattern p_space = Pattern
                .compile(REG_EX_SPACE, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll("");     /* 过滤空格回车标签 */
        /* 去掉<p>标签<br></br>标签和<>之间内容 */
        htmlStr.replaceAll("<p .*?>", "\r\n");
        htmlStr.replaceAll("<br\\s*/?>", "\r\n");
        htmlStr.replaceAll("\\<.*?>", "");
        return (htmlStr.trim());                /* 返回文本字符串 */
    }


    public static String getTextFromHtml(String htmlStr) {
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll(" ", "");
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
        return (htmlStr);
    }


    public static void main(String[] args) {

        String test = FileUtil.readString(new File("/Users/admin/IdeaProjects/learn/github/springboot-templet-start/springboot-freestyle/src/main/resources/test.txt"), "utf8");

        Document parse = Jsoup.parse(test);
        Elements elements = parse.getElementsByClass("ie-fix");
        for (Element node : elements) {
            String text = node.text();
            // text = text.replace("---------------------------------------------------------------------------------- ","").replace("实用标准 文案大全 ","");
            text = text.replace("百度文库 - 让每个人平等地提升自我", "");
            if (StrUtil.isBlank(text)) {
                continue;
            }
            String replace = text.replace("实用标准文档 文案大全 ", "\n");
            String[] arr = replace.split(" ");
            print(arr);

        }
    }

    public static void print(String[] arr) {

        String tmp = "";

        for (String node : arr) {
            String s = RegexUtils.matchStr(node, "[\u4e00-\u9fa5]");
            if (StrUtil.isBlank(s)) {
                tmp += " " + node;
            } else {
                tmp += "\n" + node;
            }
        }
        System.out.println(tmp);


    }

}
