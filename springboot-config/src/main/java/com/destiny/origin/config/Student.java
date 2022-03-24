package com.destiny.origin.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-02-21 12:39 PM
 */


@Data
@Component
public class Student {


    private String name;

    private Integer age;


    public void say(String words){
        System.out.println("say hello!" + words);
    }



}
