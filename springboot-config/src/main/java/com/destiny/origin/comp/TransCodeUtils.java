package com.destiny.origin.comp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.UnicodeUtil;

import java.io.File;

public class TransCodeUtils {

    public static void main(String[] args) {


        // F:\历史项目\202411\aries\admin\src
        String path = "\\src";

        File[] lst = FileUtil.ls(path);

        for (File file : lst) {

            transCode(file);

        }


    }


    public static void transCode(File file) {
        System.out.println(file.toString());
        String tmp = file.toString();
        if (file.isFile() && tmp.endsWith(".java")) {
            String cnt = FileUtil.readString(file, "UTF-8");
            cnt = UnicodeUtil.toString(cnt);
            System.out.println(cnt);
            FileUtil.writeString(cnt,file, "UTF-8");
            return;
        }
        if (file.isDirectory()) {
            for (File node : FileUtil.ls(file.toString())) {
                transCode(node);
            }
        }

    }
}
