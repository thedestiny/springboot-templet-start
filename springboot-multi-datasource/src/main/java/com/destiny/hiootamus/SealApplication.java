package com.destiny.hiootamus;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


// 动态数据元地址
// https://dynamic-datasource.github.io/dynamic-datasource-doc/guide/#%E7%89%B9%E6%80%A7
@Slf4j
@SpringBootApplication
@ImportResource(locations = {"classpath:config/*.xml"})
public class SealApplication {

    public static void main(String[] args) {

        log.info("start SealApplication !");
        SpringApplication.run(SealApplication.class, args);
    }

}
