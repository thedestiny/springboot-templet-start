package com.example.svndemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SvnDemoApplication {

    private static Logger logger = LoggerFactory.getLogger(SvnDemoApplication.class);

    public static void main(String[] args) {

        logger.info("dddddddddddddd");

        SpringApplication.run(SvnDemoApplication.class, args);
    }

}
