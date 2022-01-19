package com.destiny.origin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @Description springboot application
 * @Author liangwenchao
 * @Date 2021-11-26 4:45 PM
 */

@Slf4j
@SpringBootApplication
public class OriginApplication {


    public static void main(String[] args) {

        log.info("start app origin app  --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(OriginApplication.class)
                .run(args);

    }


}
