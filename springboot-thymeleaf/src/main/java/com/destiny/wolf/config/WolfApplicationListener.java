package com.destiny.wolf.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

// 理解applicationListener
// https://blog.csdn.net/liyantianmin/article/details/81017960

@Slf4j
@Component
public class WolfApplicationListener implements ApplicationListener<WolfAppEvent> {
	
	
	// ContextRefreshedEvent  被初始化或刷新时,所有的bean 被激活
	//
	
	@Override
	public void onApplicationEvent(WolfAppEvent event) {
		
		
		log.info("app event is {}", JSONObject.toJSONString(event));
		
	}
}
