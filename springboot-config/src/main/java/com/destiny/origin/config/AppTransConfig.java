package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-01-21 12:59 PM
 */

@Slf4j
@Component
public class AppTransConfig {

    @Resource
    @Qualifier(value = "hikariDataSource")
    private DataSource dataSource;

    // 声明一个事务管理器
    @Bean("platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        log.info("init platformTransactionManager ");
        return new DataSourceTransactionManager(dataSource);
    }



}
