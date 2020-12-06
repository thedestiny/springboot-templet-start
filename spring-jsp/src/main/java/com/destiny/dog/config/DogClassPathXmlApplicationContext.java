package com.destiny.dog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 扩展 applicationcontext
 * */
@Slf4j
public class DogClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {
	
	
	@Override
	protected void initPropertySources() {
		logger.info("扩展 initPropertySources");
		getEnvironment().setRequiredProperties("username");
		getEnvironment().validateRequiredProperties();
		super.initPropertySources();
	}
	
	@Override
	protected void customizeBeanFactory(DefaultListableBeanFactory beanFactory) {
		// 默认为 true
		beanFactory.setAllowBeanDefinitionOverriding(Boolean.FALSE);
		beanFactory.setAllowCircularReferences(Boolean.FALSE);
		
		super.customizeBeanFactory(beanFactory);
	}
}
