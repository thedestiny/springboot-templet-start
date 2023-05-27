package com.destiny.camel.web;

import com.alibaba.fastjson.JSONObject;
import com.destiny.camel.config.BeanLifeCycDemo;
import com.destiny.camel.entity.User;
import com.destiny.camel.event.MessageEvent;
import com.destiny.camel.service.GoodsService;
import com.destiny.camel.util.SpringContextUtils;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@Slf4j
@Controller
public class HomeController {

	private Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * curl --location --request POST 'http://localhost:9090/camel/test?name=333344&id=44444' \
	 * --header 'Content-Type: application/json' \
	 * --data-raw '{
	 *   "address":"ffffff"
	 * }'
	 * */

	@PostMapping(value = "/test")
	@ResponseBody
	public String test(@RequestBody String body, String id, String name) {
		logger.info("body is {} id is {} name is {}", body, id, name);
		return "ss";
	}

	/**
	 * 异步请求测试
	 */
	@GetMapping(value = "/async/test")
	@Async
	public String asyncTest() {

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("msg", "异步处理结果");
		return jsonObject.toString();
	}

	// 允许跨域标志
	@CrossOrigin(origins = {"http://localhost:4000"})
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home(HttpServletRequest request) {

		BeanLifeCycDemo demo = applicationContext.getBean(BeanLifeCycDemo.class);
		System.out.println(demo);
		// sync.lock();
		String url = request.getRequestURI();
		String date = new Date().toString();
		logger.info(" home is {} and date {}", url, date);
		// sync.unlock();
		return "home";
	}

	@GetMapping(value = "order")
	@ResponseBody
	public String order() {

		// http://localhost:9090/order

		int num = goodsService.order();
		JsonObject jsonObject = new JsonObject();
		return jsonObject.toString();
	}

	@GetMapping(value = "/retry")
	@ResponseBody
	public String retryTest() {
		try {
			return goodsService.retryExampleTest("1") + "";
		} catch (Exception e) {
			return "";
		}
	}


	/**
	 * home
	 */
	@GetMapping(value = "/home")
	@ResponseBody
	public User home() {

		applicationContext.publishEvent(new MessageEvent("dd", "这是一个请求消息"));

		ApplicationContext context = SpringContextUtils.getContext();
		Object homeController = context.getBean("homeController");

		System.out.println(JSONObject.toJSONString(homeController));

		User user = new User();
		user.setPassword("1244444");
		user.setIdCard("4333333");
		user.setBirthday(new Date());
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setAge(20);
		user.setWeight(new BigDecimal("23.9"));
		user.setId(23L);
		user.setUsername("小明");
		user.setSalt("qwe");
		user.setNickname("xxx");
		user.setCellphone("13897084762");

		return user;

	}

	/**
	 * value：抛出指定异常才会重试
	 * include：和value一样，默认为空，当exclude也为空时，默认所有异常
	 * exclude：指定不处理的异常
	 * maxAttempts：最大重试次数，默认3次
	 * backoff：重试等待策略，
	 * 默认使用@Backoff，@Backoff的value默认为1000L，我们设置为2000； 以毫秒为单位的延迟（默认 1000）
	 * multiplier（指定延迟倍数）默认为0，表示固定暂停1秒后进行重试，如果把multiplier设置为1.5，则第一次重试为2秒，第二次为3秒，第三次为4.5秒。
	 *
	 * @return
	 */
	@Retryable(value = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 2000, multiplier = 1.5))
	public String test() throws InterruptedException {
		TimeUnit.MICROSECONDS.sleep(200);
		log.info("test被调用,时间：" + LocalTime.now());
		int num = 4 / 0;
		return "ss";
	}


}
