package com.destiny.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RabbitApplicationContext implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		RabbitApplicationContext.context = applicationContext;
	}

	/**
	 * 获取applicationContext
	 *
	 * @return
	 */
	private static ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * 指定name获取 Bean
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 指定class获取Bean
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 指定 name,以及 clazz 返回指定的 Bean
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}
