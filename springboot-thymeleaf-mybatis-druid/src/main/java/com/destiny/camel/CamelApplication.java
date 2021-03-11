package com.destiny.camel;

import com.destiny.camel.config.BeanLifeCycDemo;
import com.destiny.camel.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.retry.RetryListener;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.listener.RetryListenerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableRetry
@EnableAsync
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
// redis 共享 session
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400 * 30)
@MapperScan("com.destiny.camel.mapper")
public class CamelApplication {
	
	@Bean
	public RetryListener retryListener() {
		log.info("retry listener !");
		return new RetryListenerSupport();
	}
	
	@Bean(initMethod = "initConfig", destroyMethod = "destroyConfig")
	@Lazy
	public BeanLifeCycDemo demo() {
		log.info("create bean ");
		return new BeanLifeCycDemo();
	}
	
	
	public static void main(String[] args) {
		
		log.info("start CamelApplication !");
		// SpringApplication.run(CamelApplication.class, args);
		
		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		//是否是web环境
		ConfigurableApplicationContext context1 = builder.web(WebApplicationType.SERVLET)
				.sources(CamelApplication.class)
				//指定配置文件
				// .properties("spring.config.location=classpath:/test-folder/my-config.properties")
				.properties("test-key", "test-value-测试值")
				// 指定配置环境
				// .properties("dev,test")
				.run(args);
		// 创建第二个 springboot 容器
		// SpringApplicationBuilder builder = new SpringApplicationBuilder();
		// //是否是web环境
		// ConfigurableApplicationContext context1 = builder.web(WebApplicationType.SERVLET)
		// 		//指定配置文件
		// 		// .properties("spring.config.location=classpath:/test-folder/my-config.properties")
		// 		.properties("test-key", "test-value-测试值")
		// 		// 指定配置环境
		// 		// .properties("dev,test")
		// 		.run(args);
		
		ApplicationContext context = SpringContextUtils.getContext();
		Object camelFactoryBean = context.getBean("camelFactoryBean");
		Object studentBeanTest = context.getBean("studentBeanTest");
		Object student = context.getBean("studentBeanTest");
		System.out.println(camelFactoryBean);
		System.out.println(studentBeanTest);
		System.out.println(student);
		
		
	}
	
}
