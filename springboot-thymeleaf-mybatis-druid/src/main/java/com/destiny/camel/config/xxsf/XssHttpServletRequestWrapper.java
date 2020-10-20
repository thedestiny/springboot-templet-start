package com.destiny.camel.config.xxsf;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**

 
 */
@Slf4j
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
	
	/**
	 *
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}
	
	@Override
	public ServletInputStream getInputStream() throws IOException {
		//非json类型，直接返回
		if (!MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(super.getHeader(HttpHeaders.CONTENT_TYPE))) {
			return super.getInputStream();
		}
		
		//为空，直接返回
		String json = IOUtils.toString(super.getInputStream(), "utf-8");
		if (StrUtil.isBlank(json)) {
			return super.getInputStream();
		}
		
		//xss过滤
		json = xssEncode(json);
		final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes("utf-8"));
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}
			
			@Override
			public boolean isReady() {
				return false;
			}
			
			@Override
			public void setReadListener(ReadListener readListener) {
			
			}
			
			@Override
			public int read() {
				return bis.read();
			}
		};
	}
	
	@Override
	public String getHeader(String name) {
		String strHeader = super.getHeader(name);
		if (StrUtil.isEmpty(strHeader)) {
			return strHeader;
			
		}
		return Jsoup.clean(super.getHeader(name), Whitelist.relaxed());
	}
	
	@Override
	public String getParameter(String name) {
		String strParameter = super.getParameter(name);
		if (StrUtil.isEmpty(strParameter)) {
			return strParameter;
		}
		return Jsoup.clean(super.getParameter(name), Whitelist.relaxed());
	}
	
	
	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if (values == null) {
			return values;
		}
		int length = values.length;
		String[] escapseValues = new String[length];
		for (int i = 0; i < length; i++) {
			//过滤一切可能的xss攻击字符串
			//   白名单  Whitelist
			escapseValues[i] = xssEncode(values[i]);
			if (!StrUtil.equals(escapseValues[i], values[i])) {
				log.info("xss字符串过滤前：" + values[i] + "\r\n" + "过滤后：" + escapseValues[i]);
			}
		}
		return escapseValues;
	}
	
	private String xssEncode(String input) {
		return Jsoup.clean(input, Whitelist.none()).trim();
	}
}
