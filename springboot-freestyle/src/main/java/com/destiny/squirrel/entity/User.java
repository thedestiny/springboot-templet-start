package com.destiny.squirrel.entity;

import lombok.Data;

import java.io.Serializable;


@Data
public class User extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 7926101484561397327L;
	
	private String name;
	private Long id;
	
	private String address;
	
	private String email;
	
	
	public User(String name) {
		this.name = name;
	}
	
	
	public User() {
	}
}
