package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Description
 * @Author destiny
 * @Date 2022-01-21 12:59 PM
 */

@Slf4j
@Component
public class AppTransConfig {

    @Autowired
    private DataSource dataSource;

    // 声明一个事务管理器
    // @Qualifier("dataSource") DataSource dataSource
    @Bean("platformTransactionManager")
    public PlatformTransactionManager platformTransactionManager() {
        log.info("init platformTransactionManager ");
        return new DataSourceTransactionManager(dataSource);
    }

//    @Bean(name = "dataSource")//注入到这个容器
//    @ConfigurationProperties(prefix = "spring.datasource")//表示取application.properties配置文件中的前缀
//    // @Primary
//    // primary是设置优先，因为有多个数据源，在没有明确指定用哪个的情况下，会用带有primary的，这个注解必须有一个数据源要添加
//    public DataSource dataSource() {
//        log.info("data source init ");
//        return DataSourceBuilder.create().build();
//    }


}
