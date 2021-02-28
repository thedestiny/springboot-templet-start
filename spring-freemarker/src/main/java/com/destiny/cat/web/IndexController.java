package com.destiny.cat.web;

import com.destiny.cat.entity.User;
import com.destiny.cat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	/**
	 * 主界面
	 */
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {
		
		new BigDecimal(0.3);
		
		String uri = request.getRequestURI();
		User user = userService.findUserById(1L);
		logger.info(" access uri is {} and user is {}", uri, user);
		
		return "home";
	}
	
	
	@RequestMapping(value = "/index/data")
	@ResponseBody
	public String index() {
		return "333344";
	}
	
	
}
