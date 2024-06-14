package com.destiny.origin.config;

import cn.hutool.core.annotation.AnnotationUtil;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

@Component
public class AppMappingHandlerMapping extends RequestMappingHandlerMapping {


    private static final Map<HandlerMethod, RequestMappingInfo> MAPPING_INFO_MAP = Maps.newHashMap();

    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
        MAPPING_INFO_MAP.put(handlerMethod, mapping);
        super.registerHandlerMethod(handler, method, mapping);
        // 接口 uri
        boolean b = handlerMethod.hasMethodAnnotation(NoAuth.class);
        boolean c1 = AnnotationUtil.hasAnnotation(method, NoAuth.class);
        Set<String> patterns = mapping.getPatternsCondition().getPatterns();
        if(b || c1){
            patterns.forEach(node -> {
                System.out.println(node);
            });
        }


    }


}

