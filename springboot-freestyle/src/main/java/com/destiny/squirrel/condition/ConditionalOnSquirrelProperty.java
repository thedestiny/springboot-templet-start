package com.destiny.squirrel.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
@Documented
@Conditional(SquirrelCondition.class)
public @interface ConditionalOnSquirrelProperty {
	
	/**
	 * Java 系统属性名称
	 * @return
	 */
	String name();
	
	/**
	 * Java 系统属性值
	 * @return
	 */
	String value();
	
	
}
