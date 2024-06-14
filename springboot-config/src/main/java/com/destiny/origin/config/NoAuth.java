package com.destiny.origin.config;


import java.lang.annotation.*;

/**
 * 标记是否鉴权, 作用于类或者方法上
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {


}
