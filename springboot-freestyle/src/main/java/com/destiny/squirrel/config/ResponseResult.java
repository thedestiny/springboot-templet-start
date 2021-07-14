package com.destiny.squirrel.config;

import java.lang.annotation.*;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-30 8:06 PM
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface ResponseResult {


}
