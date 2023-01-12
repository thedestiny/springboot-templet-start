package com.destiny.squirrel.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author destiny
 * @Date 2021-11-05 4:25 PM
 */
@Slf4j
public class JavaParserAnalyze {

    public static void main(String[] args) {

        String text = "12-2,12-3,12-4,13-1,14-5,14-3";
        Map<String, List<String>> result = new HashMap<>();
        // 循环字符串解析将数据进行分组
        for (String node : text.split(",")) {
            String[] tmpArray = node.split("-");
            // 如果不存在则创建一个列表，并添加元素
            result.computeIfAbsent(tmpArray[0], element -> new ArrayList<>()).add(tmpArray[1]);
        }
        // 打印分组结果
        result.forEach((key, value) -> {
            System.out.println("key :: " + key + " value -> " + value);
        });


    }
}
