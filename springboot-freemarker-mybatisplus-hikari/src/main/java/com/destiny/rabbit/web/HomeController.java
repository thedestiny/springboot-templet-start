package com.destiny.rabbit.web;

import com.destiny.rabbit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {
		
		String url = request.getRequestURI();
		String date = new Date().toString();
		logger.info(" home is {} and date {}", url, date);
		
		return "home";
	}
}
