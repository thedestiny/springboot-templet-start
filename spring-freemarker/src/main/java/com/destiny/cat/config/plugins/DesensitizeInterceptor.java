package com.destiny.cat.config.plugins;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Stream;

/**
 * mybatis 脱敏数据修改
 * */
@Slf4j
@Component
@Intercepts(@Signature(type = ResultSetHandler.class, method = "handleResultSets", args = Statement.class))
public class DesensitizeInterceptor implements Interceptor {
	
	/**
	 * ObscureMybatisInterceptor
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		List<Object> records = (List<Object>) invocation.proceed();
		handleDesensitize(records);
		return records;
	}
	
	
	private void handleDesensitize(List<Object> records) {
		for (Object obj : records) {
			Class<?> aClass = obj.getClass();
			MetaObject metaObject = SystemMetaObject.forObject(obj);
			Stream.of(aClass.getDeclaredFields())
					.filter(field -> field.isAnnotationPresent(Sensitive.class))
					.forEach(field -> doDesensitize(metaObject, field));
		}
	}
	
	/**
	 * 字段值脱敏
	 */
	private void doDesensitize(MetaObject metaObject, Field field) {
		// 参数名称
		String name = field.getName();
		// 参数值
		Object value = metaObject.getValue(name);
		if (String.class == metaObject.getGetterType(name) && value != null) {
			Sensitive annotation = field.getAnnotation(Sensitive.class);
			SensitiveRule rule = annotation.value();
			String modifyValue = rule.desensitizer.apply((String) value);
			metaObject.setValue(name, modifyValue);
		}
		
		
	}
	
}
