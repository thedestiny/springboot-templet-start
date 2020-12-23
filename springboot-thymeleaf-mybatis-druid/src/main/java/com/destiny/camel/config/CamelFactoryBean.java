package com.destiny.camel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamelFactoryBean implements FactoryBean {
	
	
	@Override
	public Object getObject() throws Exception {
		UserBeanTest userBeanTest = new UserBeanTest();
		System.out.println(userBeanTest);
		return userBeanTest;
	}
	
	@Override
	public Class<?> getObjectType() {
		return UserBeanTest.class;
	}
}
