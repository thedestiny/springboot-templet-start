package com.destiny.origin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-01-21 12:59 PM
 */

@Component
public class AppTransConfig {


    // 声明一个事务管理器
    @Bean("platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        return new DataSourceTransactionManager();
    }



}
