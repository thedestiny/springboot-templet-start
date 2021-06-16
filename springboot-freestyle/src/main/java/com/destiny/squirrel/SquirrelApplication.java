package com.destiny.squirrel;

import com.destiny.squirrel.condition.ConditionalOnSquirrelProperty;
import com.destiny.squirrel.config.EnableSquirrelConfig;
import com.destiny.squirrel.service.CalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
@EnableSquirrelConfig
public class SquirrelApplication {
	
	/**
	 * 选取系统名称
	 * */
	@Bean
	@ConditionalOnSquirrelProperty(name = "user.name", value = "xieyue") // 条件装配
	public String helloWord(){
		log.info("装配成功!");
		return "hello world";
	}
	
	
	public static void main(String[] args) {
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		ConfigurableApplicationContext context = builder.sources(SquirrelApplication.class)
				.web(WebApplicationType.SERVLET)
				// .profiles("prod")
				.properties("server.port", "9087")
				.run(args);
		if(context.containsBean("squirrel")){
			
			String cont = context.getBean("squirrel",String.class);
			log.info("cont is " + cont);
		}
		/* profile */
		CalculateService service = context.getBean(CalculateService.class);
		Integer sum = service.sum(23,34,5,56,67);
		log.info("calculate sum is = " + sum);
		
		/* 条件装配 */
		// String hello = context.getBean("helloWord",String.class);
		// log.info("hello = " + hello);
		
	}
	
}
