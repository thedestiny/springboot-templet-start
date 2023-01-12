package com.destiny.squirrel.config;

import java.lang.annotation.*;

/**
 * @Description
 * @Author destiny
 * @Date 2021-06-30 8:06 PM
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ResponseResult {


}
