package com.destiny.rabbit.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class MybatisPlusConfig {
	
	/**
	 * 分页插件 PaginationInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		// PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor();
		// return paginationInterceptor;

		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		// paginationInterceptor.setLimit(500);
		// 开启 count 的 join 优化,只针对部分 left join
		//这是分页拦截器
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setOverflow(true);
		paginationInnerInterceptor.setMaxLimit(500L);
		mybatisPlusInterceptor.addInnerInterceptor(paginationInnerInterceptor);

		return mybatisPlusInterceptor;
	}
	
	
	//注册乐观锁插件
	@Bean
	public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
		return new OptimisticLockerInnerInterceptor();
	}

	@Autowired
	private DataSource dataSource;

	/**
	 * 声明事务管理器
	 * */
	@Bean
	public PlatformTransactionManager platformTransactionManager(){
		return new DataSourceTransactionManager(dataSource);
	}

}
