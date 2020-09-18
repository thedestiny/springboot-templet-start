package com.destiny.rabbit.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

@Slf4j
public class RabbitSpringContextUtils {
	
	/**
	 * 获取 ApplicationContext 有3中方式
	 * 1 直接注入  ApplicationContext
	 * 3 通过  implements ApplicationContextAware 接口
	 * 4 通过
	 */
	
	private static ApplicationContext context;
	
	//设置上下文
	public static void setApplicationContext(ApplicationContext applicationContext) {
		RabbitSpringContextUtils.context = applicationContext;
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
