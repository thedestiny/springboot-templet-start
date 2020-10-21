package com.destiny.shrimp;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@MapperScan("com.destiny.shrimp.mapper")
public class ShrimpApplication {

    public static void main(String[] args) {
	
	    log.info("start ShrimpApplication !");
        SpringApplication.run(ShrimpApplication.class, args);
    }

}
