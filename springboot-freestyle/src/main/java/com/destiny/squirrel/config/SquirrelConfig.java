package com.destiny.squirrel.config;

import org.springframework.context.annotation.Bean;


public class SquirrelConfig {
	
	@Bean
	public String squirrel(){
		return "squirrel bean";
	}



}
