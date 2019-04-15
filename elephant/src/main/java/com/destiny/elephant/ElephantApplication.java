package com.destiny.elephant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElephantApplication {

    private static Logger logger = LoggerFactory.getLogger(ElephantApplication.class);

    public static void main(String[] args) {


        logger.info("start ->  ");

        SpringApplication.run(ElephantApplication.class, args);
    }

}
