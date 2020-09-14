package com.destiny.example;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.example.svmdemo.mapper")
public class TempletApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(TempletApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TempletApplication.class);
    }


    public static void main(String[] args) {

        logger.info("dddddddddddddd");

        SpringApplication.run(TempletApplication.class, args);
    }

}
