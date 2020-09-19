package com.destiny.wolf.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	
	@Autowired
	@Qualifier("taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Override
	public void configureAsyncSupport(final AsyncSupportConfigurer configurer) {
		//处理 callable超时
		configurer.setDefaultTimeout(60 * 1000);
		configurer.setTaskExecutor(taskExecutor);
		configurer.registerCallableInterceptors(timeoutCallableProcessingInterceptor());
	}
	
	@Bean
	public TimeoutCallableProcessingInterceptor timeoutCallableProcessingInterceptor() {
		return new TimeoutCallableProcessingInterceptor();
	}
	
	
}
