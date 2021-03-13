package com.destiny.squirrel.condition;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;


/**
 * 条件装配
 * */

@Slf4j
public class SquirrelCondition implements Condition {
	
	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnSquirrelProperty.class.getName());
		String propertyName = String.valueOf(attributes.get("name"));
		String propertyValue = String.valueOf(attributes.get("value"));
		String javaPropertyValue = System.getProperty(propertyName);
		System.out.println(javaPropertyValue);
		return propertyValue.equals(javaPropertyValue);
	}
	
}
