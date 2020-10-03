package com.destiny.dog.service.impl;

import com.destiny.dog.service.StudentService;
import com.destiny.dog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private UserService userService;
	
	
}
