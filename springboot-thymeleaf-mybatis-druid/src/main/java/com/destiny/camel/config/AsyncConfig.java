package com.destiny.camel.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {
	
	/**
	 * 1 配置 AsyncExecutor
	 * 2 配置异常处理器 AsyncUncaughtExceptionHandler
	 * 3 application 启动类启动 EnableAsync
	 * 4 controller 启动 @Asnyc
	 *
	 * */
	
	@Override
	public Executor getAsyncExecutor() {
		
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		// 存活时间
		taskExecutor.setKeepAliveSeconds(60);
		// 核心线程数 最大线程数 核心线程超时关闭 队列最大容量
		taskExecutor.setCorePoolSize(2);
		taskExecutor.setMaxPoolSize(10);
		taskExecutor.setAllowCoreThreadTimeOut(true);
		taskExecutor.setQueueCapacity(20);
		// 线程前缀
		taskExecutor.setThreadNamePrefix("camel-async-");
		// 拒绝策略，直接丢弃
		taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		// 等待完成任务后销毁
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		taskExecutor.setAwaitTerminationSeconds(60);
		return taskExecutor;
		
	}
	
	// 用于捕获异常信息
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new CamelAsyncExceptionHandler();
	}
	
	
	@Slf4j
	static class CamelAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
		
		@Override
		public void handleUncaughtException(Throwable throwable, Method method, Object... objects) {
			
			/**
			 * transient Object[] elementData;
			 * 不参与序列化，以实际的数据长度进行序列化，减少数据传输的数量
			 * */
			List<String> list = new ArrayList<>();
			log.error("异步任务出差, method {} paeameters {}, exception {}", method.getName(), JSONObject.toJSONString(objects), throwable.getMessage());
		}
	}
	
}
