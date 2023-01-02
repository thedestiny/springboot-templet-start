package com.sk.manage.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/**")
public class MyAppFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		log.info("uri is ",req.getRequestURI() );
		filterChain.doFilter(request,response);
	}
	
	@Override
	public void destroy() {
	
	}
}
