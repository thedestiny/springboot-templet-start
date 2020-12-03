package com.destiny.dog.util.agent;

import lombok.Data;

@Data
public class UserTest {
	
	private Integer id;
	private String name;
	private int age;
	
	public UserTest(Integer id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}
	// getter setter

}
