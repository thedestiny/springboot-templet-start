package com.destiny.dog.service.impl;

import com.destiny.dog.service.StudentService;
import com.destiny.dog.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentServiceImpl implements StudentService {
	
	
	private UserService user;
	
	public void setUser(UserService user) {
		this.user = user;
	}
}
