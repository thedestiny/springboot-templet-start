package com.destiny.camel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Slf4j
public class BeanLifeCycDemo implements InitializingBean, DisposableBean {
	/**
	 *
	 *  bean 初始化顺序 1 PostConstruct 2 InitializingBean#afterPropertiesSet 3 @Bean initMethod
	 *  destroy 顺序  1 PreDestroy 2 DisposableBean#destroy 3 @Bean destroyMethod
	 *
	 * */
	
	@PostConstruct
	public void init(){
		log.info("BeanLifeCycDemo PostConstruct init");
	}
	
	@PreDestroy
	public void doDestroy(){
		log.info("BeanLifeCycDemo PreDestroy doDestroy");
	}
	
	public void initConfig(){
		log.info("BeanLifeCycDemo initConfig");
		
	}
	
	public void destroyConfig(){
		log.info("BeanLifeCycDemo destroyConfig");
	}
	
	@Override
	public void destroy() throws Exception {
		log.info("DisposableBean destroy");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("InitializingBean afterPropertiesSet");
	}
}
