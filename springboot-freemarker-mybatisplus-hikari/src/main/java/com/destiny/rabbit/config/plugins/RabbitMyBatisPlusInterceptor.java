package com.destiny.rabbit.config.plugins;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Properties;

@Slf4j
@Component
@Intercepts({
		@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class})
})
public class RabbitMyBatisPlusInterceptor implements Interceptor {
	
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 获取 StatementHandler
		StatementHandler statementHandler = PluginUtils.realTarget(invocation.getTarget());
		log.info("intercept");
		// 获取参数
		Object[] args = invocation.getArgs();
		log.info("args is {}", JSONObject.toJSONString(args));
		
		// 绑定的sql 信息
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql();
		
		Field field = ReflectionUtils.findField(boundSql.getClass(), "sql");
		if(ObjectUtil.isNotEmpty(field)){
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, boundSql, sql.toUpperCase());
		}
		
		return invocation.proceed();
	}
	
	
	@Override
	public Object plugin(Object target) {
		return target;
	}
	
	@Override
	public void setProperties(Properties properties) {
	
	}
}
