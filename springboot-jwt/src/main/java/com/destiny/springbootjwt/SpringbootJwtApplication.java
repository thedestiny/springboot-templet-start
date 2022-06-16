package com.destiny.springbootjwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringbootJwtApplication {

    public static void main(String[] args) {

        log.info(" start app !");
        SpringApplication.run(SpringbootJwtApplication.class, args);
    }

}
