package com.destiny.lobster.web.web;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.destiny.lobster.service.service.OrderService;
import com.google.gson.JsonObject;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;

@Controller
public class HomeController {
	
	private Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private OrderService orderService;
	
	@NacosValue(value = "${app.name}", autoRefreshed = true)
	private String appName;
	
	
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {
		
		// RpcContext.getContext().
		// RpcContext
		// sync.lock();
		
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(23);
		// DefaultSingletonBeanRegistry
		
		String url = request.getRequestURI();
		String date = new Date().toString();
		logger.info("appName is {}", appName);
		logger.info(" home is {} and date {}", url, date);
		// sync.unlock();
		return "home";
	}
	
	@GetMapping(value = "order")
	@ResponseBody
	public String order() {
		
		// http://localhost:9090/order
		
		int num = orderService.order();
		JsonObject jsonObject = new JsonObject();
		return jsonObject.toString();
	}
	
	
}
