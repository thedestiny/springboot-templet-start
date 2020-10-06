package com.destiny.dog.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class MyDogFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		filterChain.doFilter(servletRequest,servletResponse);
	}
	
	@Override
	public void destroy() {
	
	}
}
