package com.destiny.shrimp;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// 如果是服务注册发现  @EnableDiscoveryClient
@Slf4j
@EnableDubbo
@SpringBootApplication
@MapperScan("com.destiny.shrimp.mapper")
public class ShrimpApplication {

    public static void main(String[] args) {
	
	    log.info("start ShrimpApplication !");
        SpringApplication.run(ShrimpApplication.class, args);
    }

}
