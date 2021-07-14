package com.destiny.squirrel.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-30 8:13 PM
 */

@Slf4j
@Component
public class ResponseResultInterceptor implements HandlerInterceptor {

    public static final String RESPONSE_RESULT_ANN = "RESPONSE_RESULT_ANN";

    /**
     * 该方法将在请求处理之前进行调用
     * 存在多个 Interceptor 链式调用,按照顺序执行
     * */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) {

        // 是否此请求返回的值需要包装，其实就是运行的时候，解析@ResponseResult注解
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> klass = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            if (klass.isAnnotationPresent(ResponseResult.class)) {
                req.setAttribute(RESPONSE_RESULT_ANN, klass.getAnnotation(ResponseResult.class));
            }
            if(method.isAnnotationPresent(ResponseResult.class)){
                req.setAttribute(RESPONSE_RESULT_ANN, method.getAnnotation(ResponseResult.class));
            }
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
