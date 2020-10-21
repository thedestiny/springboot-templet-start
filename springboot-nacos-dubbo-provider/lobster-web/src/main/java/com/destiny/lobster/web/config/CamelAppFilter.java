package com.destiny.lobster.web.config;

import lombok.extern.slf4j.Slf4j;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class CamelAppFilter implements Filter {
	
	// https://github.com/Neveryu/vue-cms
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
	
	}
}
