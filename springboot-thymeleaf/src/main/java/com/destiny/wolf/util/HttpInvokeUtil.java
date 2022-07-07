package com.destiny.wolf.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-06-29 5:03 PM
 */
public class HttpInvokeUtil {

    public static void main(String[] args) {



    }


    public static void invoke(){

        HttpRequest post = HttpUtil.createPost("http://dbaexecsql.longfor.com/data_application/app_query/");

        post.header("Content-type", "");
        post.header("Cookie", "");
        Map<String,String> params = new HashMap<>();
        post.form();

        String body = post.execute().body();
    }
}
