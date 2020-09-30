package com.destiny.rabbit;

import com.destiny.rabbit.utils.RabbitSpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.destiny.rabbit.mapper")
public class RabbitApplication {
	
	
	public static void main(String[] args) {
		
		log.info("start RabbitApplication !");
		ConfigurableApplicationContext run = SpringApplication.run(RabbitApplication.class, args);
		RabbitSpringContextUtils.setApplicationContext(run);
		
	}
	
}
