package com.destiny.dog.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Slf4j
public class IOCTestCase {
	
	
	public static void main(String[] args) {
		
		// application context
		ClassPathXmlApplicationContext context =
				new ClassPathXmlApplicationContext("config/learn_bean.xml");
		
		// 1  AbstractApplicationContext.refresh()
		// 2
		//   AbstractAutowireCapableBeanFactory
		//   SimpleInstantiationStrategy
		//   DefaultSingletonBeanRegistry
		// BeanFactoryPostProcessor
		// BeanPostProcessor
		
		log.info("context is {}", JSONObject.toJSONString(context));
		
		
		
		
	}
	
	
	
}
