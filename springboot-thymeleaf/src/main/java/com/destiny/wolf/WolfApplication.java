package com.destiny.wolf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
// @EnableAspectJAutoProxy(exposeProxy = true)
public class WolfApplication {
	
	public static void main(String[] args) {
		log.info("start WolfApplication !");
		SpringApplication.run(WolfApplication.class, args);
	}
	
}
