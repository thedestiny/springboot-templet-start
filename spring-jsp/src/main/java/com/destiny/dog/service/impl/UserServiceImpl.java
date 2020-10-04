package com.destiny.dog.service.impl;

import com.destiny.dog.service.StudentService;
import com.destiny.dog.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserServiceImpl implements UserService {
	
	private StudentService student;
	
	public void setStudent(StudentService student) {
		this.student = student;
	}
}
