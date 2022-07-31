package com.destiny.elastic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class ElasticApplication {

    public static void main(String[] args) {

        log.info("start app ->>  " );
        SpringApplication.run(ElasticApplication.class, args);

    }

}
