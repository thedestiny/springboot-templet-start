package com.destiny.example;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.destiny.exmaple.mapper")
public class TempletApplication {

    private static Logger logger = LoggerFactory.getLogger(TempletApplication.class);

    public static void main(String[] args) {
    	
    	logger.info("start SvnDemoApplication !");
        SpringApplication.run(TempletApplication.class, args);
    }

}
