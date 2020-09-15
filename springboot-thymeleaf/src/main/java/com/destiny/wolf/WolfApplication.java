package com.destiny.wolf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WolfApplication {

    private static Logger logger = LoggerFactory.getLogger(WolfApplication.class);

    public static void main(String[] args) {
	    logger.info("start WolfApplication !");
        SpringApplication.run(WolfApplication.class, args);
    }

}
