package com.destiny.camel.service;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-18 9:56 AM
 */
public interface RedisService {

    /**
     * 获取值
     */
    String get(String key);

    /**
     * 设置值
     */
    String set(String key, String value, Integer expire);

    /**
     * 自增
     */
    Long incr(String key);

}
