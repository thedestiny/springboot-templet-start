package com.destiny.horse.web;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Controller
public class HomeController {

	@Autowired
	private RestTemplate restTemplate;


	
	@GetMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {


		// reset template 发起请求
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Content-Type", "application/json");
		
		JSONObject json = new JSONObject();
        json.put("data", "123");
        json.put("msg", "消息详情");
		HttpEntity<JSONObject> httpEntity = new HttpEntity<>(json, httpHeaders);

		ResponseEntity<JSONObject> resp = restTemplate.postForEntity("url", httpEntity, JSONObject.class);
		JSONObject respBody = resp.getBody();



		String url = request.getRequestURI();
		String date = new Date().toString();
		log.info(" home is {} and date {}", url, date);
		
		return "home";
	}
}
