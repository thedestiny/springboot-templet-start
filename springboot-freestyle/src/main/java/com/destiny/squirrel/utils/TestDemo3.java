package com.destiny.squirrel.utils;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-07-24 8:31 PM
 */
public class TestDemo3 {




    public static void main(String[] args) {

        String node = "";


        for(String val : node.split("\n")){

            if(val.contains("Test.")){
                continue;
            }
            if(val.contains("Controller.")){
                continue;
            }
            val = val.replaceAll("\\(.*?\\)|\\)|（.*?）|）", "").trim();
            System.out.println(val);
        }

    }

}
