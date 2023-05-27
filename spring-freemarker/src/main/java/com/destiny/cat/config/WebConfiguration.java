package com.destiny.cat.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
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

	@Autowired
	@Qualifier("bean1")
	private BeanName beanName1;

	@Resource(name = "bean2")
	private BeanName beanName2;




	InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	}

	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter(){
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		// converter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
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
