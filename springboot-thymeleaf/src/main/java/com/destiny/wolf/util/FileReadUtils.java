package com.destiny.wolf.util;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**

 */
public class FileReadUtils {

    public static void main(String[] args) throws Exception{
        // 数据存放的路径
        String ph = "/Users/admin/Desktop/datalist/";
        File file = new File(ph);
        // 获取路径下的所有文件
        String[] list = file.list();
        // 循环遍历
        for (String s : list) {
            System.out.println("文件名 " + s);
            // 原始文件  目标文件
            method1(ph + s, ph + "1" + s);
            method2(ph + s, ph + "2" + s);
        }


    }



    public static void method1(String f1, String f2) {
        String regEx = "['   ']+"; // 一个或多个空格

        Pattern p = Pattern.compile(regEx);

        try {
            String encoding = "UTF8"; // 字符编码(可解决中文乱码问题 )
            File file = new File(f1);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                FileOutputStream out = null;
                out = new FileOutputStream(new File(f2));
                String lineTXT = null;
                int count = 0;
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    count += 1;
                    Matcher m = p.matcher(lineTXT);
                    String str = count + m.replaceAll(",").trim();
                    System.out.println(str.substring(0, str.length() - 1));
                    out.write((str.substring(0, str.length() - 1) + "\n").getBytes());
                }
                read.close();
                out.close();
            } else {
                System.out.println("找不到指定的文件！");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
    }

    public static void method2(String f1, String f2) throws Exception {

        //读取文件
        FileInputStream fileInputStream = new FileInputStream(f1);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String str = "";
        StringBuilder sb = new StringBuilder();
        while ((str = bufferedReader.readLine()) != null) {

            String tmp = str.replace("	", ",");

            tmp = tmp.trim();
            if(tmp != null && !tmp.equals("")){
                sb.append(tmp + "\n");
            }


        }
        //替换文中指定字符串
        String newStr = sb.toString();

        //写入某个文件夹中
        File file = new File(f2);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        bufferedWriter.write(newStr);
        //注意flush
        bufferedWriter.flush();

    }

}
