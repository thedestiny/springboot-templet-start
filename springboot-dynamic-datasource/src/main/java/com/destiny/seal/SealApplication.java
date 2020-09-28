package com.destiny.seal;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 动态数据元地址
// https://dynamic-datasource.github.io/dynamic-datasource-doc/guide/#%E7%89%B9%E6%80%A7
@Slf4j
@SpringBootApplication
@MapperScan("com.destiny.seal.mapper")
public class SealApplication {

    public static void main(String[] args) {
	
	    log.info("start SealApplication !");
        SpringApplication.run(SealApplication.class, args);
    }

}
