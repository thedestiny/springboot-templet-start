package com.destiny.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TempletApplication {

    private static Logger logger = LoggerFactory.getLogger(TempletApplication.class);

    public static void main(String[] args) {

        logger.info("dddddddddddddd");

        SpringApplication.run(TempletApplication.class, args);
    }

}
