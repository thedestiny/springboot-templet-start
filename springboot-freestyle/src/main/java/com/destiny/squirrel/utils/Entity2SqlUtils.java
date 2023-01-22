package com.destiny.squirrel.utils;

import cn.hutool.core.util.StrUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entity2SqlUtils {


    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线
     */
    public static String humpToLine(String str) {
        str = str + "";
        return str.replaceAll("[A-Z0-9]", "_$0").toLowerCase();
    }

    private static String transType(String type) {
        if ("Integer".equals(type)) {
            return "int";
        }
        if ("Long".equals(type)) {
            return "bigint";
        }
        if ("BigDecimal".equals(type) || "Double".equals(type) || "Float".equals(type)) {
            return "decimal(18,2)";
        }
        if ("Date".equals(type)) {
            return "datetime";
        }
        if ("String".equals(type)) {
            return "varchar(255)";
        }
        if ("Boolean".equals(type)) {
            return "char(20)";
        }
        return "no";
    }

    public static void main(String[] args) {

        String entity = "Member";

        String columns = "  private Integer id;\n" +
                "    private String name;\n" +
                "    private String code;\n" +
                "    private String helpCode;\n" +
                "    private String sex;//套餐适用性别：0不限 1男 2女\n" +
                "    private String age;//套餐适用年龄\n" +
                "    private Float price;//套餐价格\n" +
                "    private String remark;\n" +
                "    private String attention;\n" +
                "    private String img;//套餐对应图片存储路径";

        String result = StrUtil.format("CREATE TABLE `{}` (\n", humpToLine(entity));


        for (String node : columns.split("\n")) {
            node = StrUtil.blankToDefault(node, "").trim();
            if (node.startsWith("private")) {
                String tmp = node.replace(" ", "-");
                String replace = tmp.replace("--", "-").split(";")[0];
                String[] split = replace.split("-");
                String field =split[2];//  humpToLine(split[2]);
                String type = transType(split[1]);
                String tp = StrUtil.format("  `{}` {}  NULL COMMENT '{}',\n", field, type, split[2]);
                if(field.equals("id")){
                    tp = StrUtil.format("  `{}` {} NOT NULL AUTO_INCREMENT COMMENT '{}',\n", field, type, split[2]);
                }

                result += tp;
            }
        }
        result += "  PRIMARY KEY (`id`) USING BTREE\n";
        result += ") ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=utf8mb4 COMMENT='" + entity+"';\n";

        System.out.println("\n" + result);

    }


}
