package com.destiny.origin;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.destiny.origin.event.NoticeListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * @Description springboot application
 * @Author liangwenchao
 * @Date 2021-11-26 4:45 PM
 */

@Slf4j
@SpringBootApplication
@MapperScan(value = "com.destiny.origin.mapper")
// 开启注解事务管理，等同于xml配置文件中的 <tx:annotation-driven />
@EnableTransactionManagement(proxyTargetClass = false, mode = AdviceMode.PROXY)
@EnableAspectJAutoProxy(exposeProxy = true)
public class OriginApplication {


    public static void main(String[] args) {

        log.info("start app origin app  --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        ConfigurableApplicationContext run = builder.sources(OriginApplication.class)
                .run(args);

        DispatcherServlet servlet = new DispatcherServlet();
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        ApplicationContext context1 = new AnnotationConfigApplicationContext(OriginApplication.class);
        NoticeListener bean = context1.getBean(NoticeListener.class);
        System.out.println(bean);

        String[] namesForType = context1.getBeanNamesForType(NoticeListener.class);
        for (String name : namesForType) {
            System.out.println(name);
        }

//        ThreadPoolTaskExecutor bean = run.getBean(ThreadPoolTaskExecutor.class);
//        System.out.println(bean);

    }


}


