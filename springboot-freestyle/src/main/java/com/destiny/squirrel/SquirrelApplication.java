package com.destiny.squirrel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@Slf4j
@SpringBootApplication
public class SquirrelApplication {
	
	public static void main(String[] args) {
		
		// SpringApplication.run(SquirrelApplication.class,args);
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		builder.sources(SquirrelApplication.class)
				.web(WebApplicationType.SERVLET)
				.profiles("prod")
				.properties("server.port","9087")
		        .run(args);
		
		/*SpringApplicationBuilder builder1 = new SpringApplicationBuilder();
		builder1.sources(SquirrelApplication.class)
				.profiles("prod")
				.properties("server.port","9088")
				.run(args);*/
	}
	
}
