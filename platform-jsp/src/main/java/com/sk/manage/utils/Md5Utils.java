package com.sk.manage.utils;

import cn.hutool.crypto.SecureUtil;

/**
 * @Description
 * @Date 2022-12-23 11:49 PM
 */
public class Md5Utils {


    public static void main(String[] args) {

        // e10adc3949ba59abbe56e057f20f883e
        String s = SecureUtil.md5("123456");
        System.out.println(s);


    }

}
