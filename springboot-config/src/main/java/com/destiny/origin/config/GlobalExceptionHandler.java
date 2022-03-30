package com.destiny.origin.config;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 全局异常捕获
 * @Author liangwenchao
 * @Date 2021-09-27 4:13 PM
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{}, 发生未知异常.", requestURI, e);
        return Throwables.getStackTraceAsString(e);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生系统异常.", requestURI, e);
        return Throwables.getStackTraceAsString(e);
    }
    /**
     * 系统异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public String handleThrowable(Throwable e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址{},发生系统异常.", requestURI, e);
        return Throwables.getStackTraceAsString(e);
    }

}

