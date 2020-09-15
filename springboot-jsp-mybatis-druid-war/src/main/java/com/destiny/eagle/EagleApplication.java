package com.destiny.eagle;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.destiny.eagle.mapper")
public class EagleApplication extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(EagleApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(EagleApplication.class);
    }


    public static void main(String[] args) {
	    logger.info("start EagleApplication !");
        SpringApplication.run(EagleApplication.class, args);
    }

}
