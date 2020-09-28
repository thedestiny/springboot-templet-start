package com.destiny.seal.web;

import com.alibaba.fastjson.JSONObject;
import com.destiny.seal.entity.User;
import com.destiny.seal.service.UserService;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping(value = "index")
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/user")
	public String user() {
		
		
		Faker faker = new Faker(Locale.CHINA);
		User user = new User();
		// user.setId(2);
		user.setSalt("dddd");
		user.setCellphone("322");
		user.setUsername(faker.name().fullName());
		user.setIdCard(faker.idNumber().ssnValid());
		user.setAge(30);
		userService.addUserInfo(user);
		
		User entity = userService.getById("1");
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		json.put("msg", "成功");
		json.put("data", entity);
		
		return json.toString();
		
	}
	
	
}
