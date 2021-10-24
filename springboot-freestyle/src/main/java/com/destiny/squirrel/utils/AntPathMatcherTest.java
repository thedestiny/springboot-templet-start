package com.destiny.squirrel.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

@Slf4j
public class AntPathMatcherTest {


    public static void main(String[] args) {

        AntPathMatcher matcher = new AntPathMatcher();
        // rest full 风格请求记录地址并且匹配
        boolean match = matcher.match("/api/user/{userId}", "/api/user/34");
        System.out.println(match);


    }

}
