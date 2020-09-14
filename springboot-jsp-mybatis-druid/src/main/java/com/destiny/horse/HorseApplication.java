package com.destiny.horse;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.destiny.horse.mapper")
public class HorseApplication {

    private static Logger logger = LoggerFactory.getLogger(HorseApplication.class);

    public static void main(String[] args) {
        logger.info("start HorseApplication !");
        SpringApplication.run(HorseApplication.class, args);
    }

}
