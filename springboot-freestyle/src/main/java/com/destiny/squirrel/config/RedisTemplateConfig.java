package com.destiny.squirrel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description
 * @Author destiny
 * @Date 2021-11-18 7:52 PM
 */
@Slf4j
public class RedisTemplateConfig {


    @Bean
    public void redisTemplate(){

        RedisTemplate<String,String> template = new RedisTemplate<>();


    }


}
