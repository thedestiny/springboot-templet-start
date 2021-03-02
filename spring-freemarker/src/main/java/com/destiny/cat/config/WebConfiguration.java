package com.destiny.cat.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
	InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
	
	
	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		
		CompletionService service = new ExecutorCompletionService(new ThreadPoolExecutor(2,3,2, TimeUnit.SECONDS,new LinkedBlockingDeque<>(34)));
		
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}
	
	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
		converter.setCharset(Charset.forName("UTF-8"));
		return converter;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//添加字符转解析器
		converters.add(stringHttpMessageConverter());
		//添加json解析器
		converters.add(fastJsonHttpMessageConverter());
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		
		
		
		converters.clear();
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
	}

	
}
