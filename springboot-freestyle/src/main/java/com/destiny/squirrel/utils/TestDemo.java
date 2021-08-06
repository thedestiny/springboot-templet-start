package com.destiny.squirrel.utils;

import cn.hutool.core.util.StrUtil;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-07-01 4:29 PM
 */
public class TestDemo {

    public static void main(String[] args) {

        String idListStr = "12,34,45 ,67,,,";
        // 获取 Id 集合
        List<String> idList = StrUtil.split(idListStr, ",").stream()
                .filter(node -> StrUtil.isNotBlank(node))
                // .map(String::trim)
                .collect(Collectors.toList());

        System.out.println(idList);

        String queryKey = String.join(":","4444", "rrrr");
        System.out.println(queryKey);


    }
}
