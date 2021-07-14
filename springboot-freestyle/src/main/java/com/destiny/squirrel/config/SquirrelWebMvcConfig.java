package com.destiny.squirrel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-07-01 11:11 AM
 */
@Configuration
public class SquirrelWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ResponseResultInterceptor interceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // super.addInterceptors(registry);
        // 注册自定义的拦截器passwordStateInterceptor
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") //匹配要过滤的路径
                .excludePathPatterns("/api/*") //匹配不过滤的路径。密码还要修改呢，所以这个路径不能拦截
                .excludePathPatterns("/api/");//版本信息同样不能拦截
    }
}
