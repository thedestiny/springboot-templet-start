package com.destiny.rabbit.config;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class MybatisPlusConfig {
	
	/**
	 * 分页插件 PaginationInterceptor
	 */
	@Bean
	public PaginationInnerInterceptor paginationInterceptor() {
		PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		return paginationInterceptor;
	}
	
	
	//注册乐观锁插件
	@Bean
	public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}
	
	
	/*@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		return paginationInterceptor;
	}*/
}
