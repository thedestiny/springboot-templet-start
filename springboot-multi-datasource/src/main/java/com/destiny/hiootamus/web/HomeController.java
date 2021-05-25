package com.destiny.hiootamus.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Controller
public class HomeController {
	
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {
		
		String url = request.getRequestURI();
		String date = new Date().toString();
		log.info(" home is {} and date {}", url, date);
		return "home";
	}
	
	
}
