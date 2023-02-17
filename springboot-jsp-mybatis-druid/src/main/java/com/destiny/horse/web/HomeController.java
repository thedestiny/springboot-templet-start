package com.destiny.horse.web;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

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




		public static void main(String[] args) throws SocketException {


			Snowflake snowflake = new Snowflake();

			long l = snowflake.nextId();

			byte[] localHardwareAddress = NetUtil.getLocalHardwareAddress();
			String ddt = NetUtil.getLocalHostName();
			String ddt2 = NetUtil.getLocalMacAddress();
			String ddt1 = NetUtil.getLocalhostStr();
			InetAddress localhost = NetUtil.getLocalhost();


			System.out.println(ddt);
			System.out.println(ddt2);
			System.out.println(ddt1);
			System.out.println(localhost);
			System.out.println(new String(localHardwareAddress));



			StringBuilder sb = new StringBuilder();
			Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			byte[] mac = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = allNetInterfaces.nextElement();
				if (netInterface.isLoopback() || netInterface.isVirtual() || netInterface.isPointToPoint() || !netInterface.isUp()) {
					continue;
				} else {
					mac = netInterface.getHardwareAddress();
					if (mac != null) {
						for (int i = 0; i < mac.length; i++) {
							sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "\n"));
						}
					}
				}
			}
			System.out.println(sb.toString());
		}



}
