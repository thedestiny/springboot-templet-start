package com.destiny.camel.config.annotation;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-18 9:29 AM
 */
public @interface AccessLimit {

    /**
     * 1 秒内最多访问10次
     *
    * */
    int seconds() default 1;

    int maxCount() default 30;

    boolean needLogin() default true;

}
