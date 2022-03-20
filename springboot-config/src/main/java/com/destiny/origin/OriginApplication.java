package com.destiny.origin;

import com.destiny.origin.event.NoticeListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Description springboot application
 * @Date 2021-11-26 4:45 PM
 */

@Slf4j
@SpringBootApplication
@MapperScan(value = "com.destiny.origin.mapper")
// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableTransactionManagement// (proxyTargetClass = false, mode = AdviceMode.ASPECTJ)
// @EnableAspectJAutoProxy// (exposeProxy = false)
public class OriginApplication {


    public static void main(String[] args) {

        log.info("start app origin app  --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        // ConfigurableApplicationContext run =
        builder.sources(OriginApplication.class).run(args);

        ResourcePatternResolver resolver = new ResourcePatternResolver();

        BeanDefinition definition = new AbstractBeanDefinition() {
            @Override
            public AbstractBeanDefinition cloneBeanDefinition() {
                return null;
            }

            @Override
            public void setParentName(String s) {

            }

            @Override
            public String getParentName() {
                return null;
            }
        };

//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//        AbstractApplicationContext ct = new AnnotationConfigApplicationContext();
//
//        DefaultSingletonBeanRegistry registry = new DefaultSingletonBeanRegistry();


//        DispatcherServlet servlet = new DispatcherServlet();
      ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");



        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext();

        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner("");


        ConfigurationClassPostProcessor processor = new ConfigurationClassPostProcessor();
//
//        ApplicationContext context1 = new AnnotationConfigApplicationContext(OriginApplication.class);
//        NoticeListener bean = context1.getBean(NoticeListener.class);
//        System.out.println(bean);
//
//        String[] namesForType = context1.getBeanNamesForType(NoticeListener.class);
//        for (String name : namesForType) {
//            System.out.println(name);
//        }

//        ThreadPoolTaskExecutor bean = run.getBean(ThreadPoolTaskExecutor.class);
//        System.out.println(bean);

    }


}


