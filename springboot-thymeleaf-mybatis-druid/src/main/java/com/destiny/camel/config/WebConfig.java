package com.destiny.camel.config;

import com.destiny.camel.interceptor.AccessLimitInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-18 12:38 PM
 */

@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AccessLimitInterceptor limitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(limitInterceptor);
        super.addInterceptors(registry);

    }
}
