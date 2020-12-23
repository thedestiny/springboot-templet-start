package com.destiny.camel.config;


import org.springframework.stereotype.Component;

@Component
public class StudentBeanTest {
	
	
	private String id;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
