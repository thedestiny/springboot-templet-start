package com.destiny.squirrel.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-07-28 2:57 PM
 */

@Slf4j
public class TestDemo4 {

    static String text = "";

    public static void main(String[] args) throws InterruptedException {


      /* while (true){
           String url = "http://localhost:9092/switch";
           String apiData = HttpUtil.get(url);
           log.info(apiData);
           TimeUnit.MILLISECONDS.sleep(100);
       }*/


        String[] split = text.split("\n");
        Set<String> se = new HashSet<>();

        List<String> lst = new ArrayList<>();
        for (String node : split) {
            if (StrUtil.isBlank(node) || node.startsWith("#")) {
                continue;
            }
            String[] split1 = node.split(" = ");

            if (se.contains(split1[0].trim())) {
                lst.add(split1[0].trim());
                continue;
            }

            se.add(split1[0].trim());
            System.out.println(node);


        }

        System.out.println("--------");
        lst.forEach(node -> System.out.println(node));


    }

}
