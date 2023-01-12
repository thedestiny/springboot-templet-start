//package com.destiny.squirrel.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * @Description
// * @Author destiny
// * @Date 2021-09-28 7:20 PM
// */
//@Slf4j
//@Component
//public class AppRequestInterceptor implements RequestInterceptor {
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
//                .getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        log.info(" request {}", request.getRequestURL());
//        log.info(" template {}", requestTemplate);
//    }
//}
