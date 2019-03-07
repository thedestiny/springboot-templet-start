package com.example.svndemo;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.example.svmdemo.mapper")
public class SvnDemoApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(SvnDemoApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SvnDemoApplication.class);
    }


    public static void main(String[] args) {

        logger.info("dddddddddddddd");

        SpringApplication.run(SvnDemoApplication.class, args);
    }

}
