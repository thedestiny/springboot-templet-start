package com.destiny.dog.learn;


import com.destiny.dog.config.MyDogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;


// https://www.cnblogs.com/liuyk-code/p/9886033.html
import java.io.Serializable;


/*						@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
						@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),*/
@Slf4j
@Configuration
@ComponentScans(value = {@ComponentScan(value = "com.destiny.dog.service", includeFilters = {
		@ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyDogFilter.class})
}, useDefaultFilters = false)})
@EnableAspectJAutoProxy
public class MainConfig implements Serializable {
	
	
	/*
	* ImportBeanDefinitionRegistrar
	*
	* */
}
