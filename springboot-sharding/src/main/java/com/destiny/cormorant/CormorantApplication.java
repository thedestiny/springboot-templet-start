package com.destiny.cormorant;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-06 5:33 PM
 */
@Slf4j
@MapperScan("com.destiny.cormorant.mapper")
@SpringBootApplication
public class CormorantApplication {

    public static void main(String[] args) {

        log.info("start app CormorantApplication --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(CormorantApplication.class)
                // .profiles("prod")
                .run(args);


    }

}
