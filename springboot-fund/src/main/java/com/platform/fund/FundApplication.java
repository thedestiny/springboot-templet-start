package com.platform.fund;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@Slf4j
@EnableScheduling
@SpringBootApplication
@MapperScan(value = "com.platform.fund.mapper")
public class FundApplication {

    public static void main(String[] args) {
        log.info(" start fund etf app !");
        SpringApplication.run(FundApplication.class, args);
    }

}
