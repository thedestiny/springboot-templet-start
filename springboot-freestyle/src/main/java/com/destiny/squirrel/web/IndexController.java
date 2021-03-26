package com.destiny.squirrel.web;

import com.destiny.squirrel.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {
	
	@PostMapping(value = "/index")
	public String index(@RequestBody User user) {
		
		log.info("user is {}", user);
		
		return "333";
	}
	
	
}
