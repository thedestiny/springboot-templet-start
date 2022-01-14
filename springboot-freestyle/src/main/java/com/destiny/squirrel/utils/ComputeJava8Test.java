package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ComputeJava8Test {

    public static void main(String[] args) {

        String dd = "12-2\n12-4\n13-6\n13-9\n14-7";
        String[] arr = dd.split("\n");

        Map<String, List<String>> result = new HashMap<>();
        Map<String, List<String>> result1 = new HashMap<>();

        for (int i = 0; i < arr.length ; i++) {
            String s = arr[i];
            String[] tmp = s.split("-");
            // computeIfAbsent 的情况
            // 12 -> [2, 4]
            // 13 -> [6, 9]
            // 14 -> [7]
            result.computeIfAbsent(tmp[0], id -> new ArrayList<>()).add(tmp[1]);

            result1.compute(tmp[0],(key,value) -> {
                System.out.println(key);
                System.out.println(value);
                if (CollUtil.isEmpty(value)){
                    value = new ArrayList<>();
                }
                value.add(key)

            ; return value;});
        }
        result.forEach((k,v) ->{
            System.out.print(k + " -> ");
            System.out.println(v);
        });
        System.out.println(result1);



    }

}
