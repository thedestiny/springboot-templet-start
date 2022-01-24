package com.destiny.origin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * @Description springboot application
 * @Author liangwenchao
 * @Date 2021-11-26 4:45 PM
 */

@Slf4j
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = false, mode = AdviceMode.PROXY)
public class OriginApplication {


    public static void main(String[] args) {

        log.info("start app origin app  --> ");
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        ConfigurableApplicationContext run = builder.sources(OriginApplication.class)
                .run(args);

        ThreadPoolTaskExecutor bean = run.getBean(ThreadPoolTaskExecutor.class);
        System.out.println(bean);

    }


}


