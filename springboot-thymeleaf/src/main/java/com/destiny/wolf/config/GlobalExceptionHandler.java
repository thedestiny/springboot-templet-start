package com.destiny.wolf.config;


import com.alibaba.fastjson.JSONObject;
import com.destiny.wolf.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// 初始化绑定器
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		System.out.println("请求有参数才进来");
	}
	
	// 初始化 绑定 model
	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("author", "test-author");
	}
	
	// 自定义异常
	@ExceptionHandler(value = AppException.class)
	public String appException(AppException exception, HttpServletRequest request){
		String msg = exception.getMsg();
		return "异常信息 :: " + msg;
		
	}
	
	// 运行时异常
	@ExceptionHandler(value = RuntimeException.class)
	public String appException(RuntimeException exception, HttpServletRequest request){
		
		String msg = exception.getMessage();
		return "异常信息 :: " + msg;
		
	}
	
	// 拦截异常请求
	@ExceptionHandler(value = Exception.class)
	public Object handleException(Exception exception, HttpServletRequest request) {
		JSONObject r = new JSONObject();
		//业务异常
		if (exception instanceof AppException) {
			r.put("code", ((AppException) exception).getCode());
			r.put("msg", ((AppException) exception).getMsg());
		} else {//系统异常
			r.put("code", "500");
			r.put("msg", "未知异常，请联系管理员");
		}
		
		//使用HttpServletRequest中的header检测请求是否为ajax, 如果是ajax则返回json, 如果为非ajax则返回view(即ModelAndView)
		String contentTypeHeader = request.getHeader("Content-Type");
		String acceptHeader = request.getHeader("Accept");
		String xRequestedWith = request.getHeader("X-Requested-With");
		if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
				|| (acceptHeader != null && acceptHeader.contains("application/json"))
				|| "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
			return r;
		} else {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("msg", exception.getMessage());
			modelAndView.addObject("url", request.getRequestURL());
			modelAndView.addObject("stackTrace", exception.getStackTrace());
			modelAndView.setViewName("error");
			return modelAndView;
		}
	}
	
	
}
