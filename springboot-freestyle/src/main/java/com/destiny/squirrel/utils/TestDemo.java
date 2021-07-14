package com.destiny.squirrel.utils;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-07-01 4:29 PM
 */
public class TestDemo {

    public static void main(String[] args) {

        String longminIds = "12,34,45 ,67,,,";
        // 获取longminId 集合
        List<String> longminIdList = Arrays.stream(StrUtil.split(longminIds, ","))
                .filter(node -> StrUtil.isNotBlank(node))
                // .map(String::trim)
                .collect(Collectors.toList());

        System.out.println(longminIdList);

        String queryKey = String.join(":","4444", "rrrr");
        System.out.println(queryKey);


    }
}
