package com.destiny.cat.config.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.sql.Statement;
import java.util.Properties;


@Slf4j
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
public class ObscureMybatisInterceptor implements Interceptor {
	
	/**
	 * ObscureMybatisInterceptor
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object proceed = invocation.proceed();
		return null;
	}
	
	@Override
	public Object plugin(Object o) {
		
		
		return null;
	}
	
	@Override
	public void setProperties(Properties properties) {
	
	}
}
