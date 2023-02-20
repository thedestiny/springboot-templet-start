package com.destiny.squirrel.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * 在线测试地址
 * https://c.runoob.com/front-end/854/
 * https://blog.csdn.net/kong_gu_you_lan/article/details/113062057
 * https://blog.csdn.net/kong_gu_you_lan/article/details/119342396
 * @Description
 * @Date 2023-02-20 3:17 PM
 */
public class RegexUtils {

    // * 0次到多次 + 1次到多次 ？ 0次或者1次

    // 正则	名称	含义	示例
    //(?<=Y)	肯定逆序环视	左边是Y	(?<=\d)th 左边是数字的 th，可以匹配上 9th
    //(?<!Y)	否定逆序环视	左边不是Y	(?<!\d)th 左边不是数字的 th，可以匹配上 health
    //(?=Y)	肯定顺序环视	右边是Y	six(?=\d) 右边是数字的 six，可以匹配上 six6
    //(?!Y)	否定顺序环视	右边不是Y	six(?!\d) 右边不是数字的 six，可以匹配上 sixgod


    public static void main(String[] args) {

        // Pattern pattern = Pattern.compile("(?<=>).+?(?=<)");
        // Pattern pattern = Pattern.compile("(?<=>).*?(?=<)");
        // Pattern pattern = Pattern.compile("(?<=>).+?(?=<)");

        Pattern pattern = Pattern.compile("((?<=>)[^>]+\\+?(?=</)|(?<=auto\"><strong>).+?(?=</))");

        String test = "<p style=\"width: auto\"><strong>2,114.15USD</strong></p >\n" +
                "<p style=\"width: auto\">消费&nbsp;失败</p >\n" +
                "<p style=\"width: auto\">530616******0099</p >\n" +
                "<p style=\"width: auto\">2022-12-20 20:17</p >";

        System.out.println(test + "\n");

        String[] split = test.split("\n");

        for (String s : split) {

            Matcher matcher = pattern.matcher(s);
            if (matcher.find()) {
                System.out.println(matcher.group());
            }

        }




    }


}
