package com.destiny.camel;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableRetry
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.destiny.camel.mapper")
public class CamelApplication {
	
	@Bean
	public RetryListener retryListener(){
		log.info("retry listener !");
		return new RetryListenerSupport();
	}
	
	
	public static void main(String[] args) {
		
		log.info("start CamelApplication !");
		SpringApplication.run(CamelApplication.class, args);
	}
	
}
