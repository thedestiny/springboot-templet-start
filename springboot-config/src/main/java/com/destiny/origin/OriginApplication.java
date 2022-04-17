package com.destiny.origin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.aop.framework.AopProxyFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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
@EnableAspectJAutoProxy// (exposeProxy = false)
public class OriginApplication {

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            //有返回值，但主线程不需要用到返回值
            TimeInterval timer = DateUtil.timer();
            log.info(Thread.currentThread().getName() + "：开始调用异步业务");
            log.info(Thread.currentThread().getName() + "：调用异步业务结束，耗时：" + timer.interval());
        };
    }


    public static void main(String[] args) {

        log.info("start app origin app  --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        // ConfigurableApplicationContext run =
        builder.sources(OriginApplication.class).run(args);


//        BeanDefinition definition = new AbstractBeanDefinition() {
//            @Override
//            public AbstractBeanDefinition cloneBeanDefinition() {
//                return null;
//            }
//
//            @Override
//            public void setParentName(String s) {
//
//            }
//
//            @Override
//            public String getParentName() {
//                return null;
//            }
//        };

//        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//         AbstractApplicationContext ct = new AnnotationConfigApplicationContext();
//
//        DefaultSingletonBeanRegistry registry = new DefaultSingletonBeanRegistry();


//        DispatcherServlet servlet = new DispatcherServlet();
//      ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");



//        AnnotationConfigApplicationContext context1 = new AnnotationConfigApplicationContext();

//        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner("");


//        ConfigurationClassPostProcessor processor = new ConfigurationClassPostProcessor();
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


