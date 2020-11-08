package com.destiny.camel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.destiny.camel.mapper")
public class CamelApplication {
	
	
	public static void main(String[] args) {
		
		log.info("start CamelApplication !");
		SpringApplication.run(CamelApplication.class, args);
	}
	
}
