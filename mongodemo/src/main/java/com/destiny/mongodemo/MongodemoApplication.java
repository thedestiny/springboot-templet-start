package com.destiny.mongodemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MongodemoApplication {

    private static Logger logger = LoggerFactory.getLogger(MongodemoApplication.class);

    public static void main(String[] args) {

        logger.info("monogdb start !");

        SpringApplication.run(MongodemoApplication.class, args);
    }

}
