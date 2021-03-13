package com.destiny.squirrel.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(SquirrelImportSelector.class)
public @interface EnableSquirrelConfig {
	
    String name() default "app";
    
    String version() default "1";
}
