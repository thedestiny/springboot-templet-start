package com.destiny.wolf.util;

import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class CHMTestDemo {

    public static void main(String[] args) {


        ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();

        Integer a1 = hashMap.computeIfAbsent("a", (key) -> 1 + 1);
        Integer a2 = hashMap.computeIfPresent("a", (key, value) -> hashMap.get(key) + value);
        System.out.println(a1);
        System.out.println(a2);
        hashMap.put("b", 3);
        hashMap.compute("b", (key, value) -> hashMap.get(key) + value);

        // 不存在时插入
        hashMap.putIfAbsent("c", 5);


        HashMap<String, Integer> chmp = new HashMap<>();

        // chmp.put("java",2);
        // 用于统计数据
        chmp.merge("java", 1, Integer::sum);
        chmp.merge("java", 1, Integer::sum);
        chmp.merge("spring", 1, Integer::sum);
        chmp.merge("java", 1, Integer::sum);
        chmp.merge("java", 1, Integer::sum);
        chmp.merge("spring", 1, Integer::sum);

        chmp.putIfAbsent("java",9);
        chmp.putIfAbsent("redis",6);

        Integer mybatis = chmp.getOrDefault("mybatis", 0);
        System.out.println(StrUtil.format("mybaits {}", mybatis));
        Integer java = chmp.getOrDefault("java", 0);
        System.out.println(StrUtil.format("java {}", java));

        chmp.computeIfAbsent("mysql", key -> 0 );

        System.out.println(chmp);

        HashMap<String, ArrayList<Integer>> chmp1 = new HashMap<>();

        chmp1.computeIfAbsent("mysql", key -> new ArrayList<>()).add(34);

        System.out.println(chmp1);
    }


}
