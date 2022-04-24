package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

@Slf4j
public class AppBeanMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
        if ("getName".equals(method.getName())) {
            log.info("调用 getName");
        }
        if ("setName".equals(method.getName())) {
            log.info("调用 setName 被修改了");
        }
        Object value = proxy.invokeSuper(obj, objects);
        log.info(" intercept is {}", value);
        return value;

    }
}
