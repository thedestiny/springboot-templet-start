package com.destiny.origin.config;

import lombok.Data;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-02-21 12:39 PM
 */
@Data
public class Student {


    private String name;

    private Integer age;


    public void say(String words){
        System.out.println("say hello!" + words);
    }



}
