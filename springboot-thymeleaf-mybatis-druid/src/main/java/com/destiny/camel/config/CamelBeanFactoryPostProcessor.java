package com.destiny.camel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CamelBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	
	/**
	 * 修改 bean 信息
	 *
	 *
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
		BeanDefinition studentBeanTest = beanFactory.getBeanDefinition("studentBeanTest");
		// studentBeanTest.setBeanClassName("student");
		studentBeanTest.setScope("prototype");
	}
}
