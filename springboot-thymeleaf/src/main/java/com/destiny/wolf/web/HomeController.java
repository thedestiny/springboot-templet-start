package com.destiny.wolf.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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
	
	// https://www.cnblogs.com/baixianlong/p/10661591.html
	// @Async("taskExecutor")
	@GetMapping(value = "test/async")
	@ResponseBody
	public Callable<String> test(HttpServletResponse response, HttpServletRequest request) {
		
		JSONObject json = new JSONObject();
		json.put("code", 1);
		json.put("msg", "请求成功!");
		
		// 返回对象
		Callable<String> callable = () -> {
			TimeUnit.SECONDS.sleep(2);
			return json.toString();
		};
		
		return callable;
		
	}
	
	
}
