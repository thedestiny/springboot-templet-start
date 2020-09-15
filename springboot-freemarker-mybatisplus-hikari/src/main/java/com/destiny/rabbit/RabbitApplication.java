package com.destiny.rabbit;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.destiny.rabbit.mapper")
public class RabbitApplication {

    private static Logger logger = LoggerFactory.getLogger(RabbitApplication.class);

    public static void main(String[] args) {
    	
    	logger.info("start RabbitApplication !");
        SpringApplication.run(RabbitApplication.class, args);
    }

}
