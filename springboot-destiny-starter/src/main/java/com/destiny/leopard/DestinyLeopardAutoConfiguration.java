package com.destiny.leopard;

import com.destiny.leopard.service.DestinyService;
import com.destiny.leopard.service.impl.LeopardDestinyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-10 10:10 AM
 */
@Configuration
@ConditionalOnClass(DestinyService.class)
@EnableConfigurationProperties(LeopardProperties.class)
public class DestinyLeopardAutoConfiguration {

    @Autowired
    private LeopardProperties properties;

    @Bean
    // 当容器没有这个 Bean 的时候才创建这个 Bean
    @ConditionalOnMissingBean(DestinyService.class)
    @ConditionalOnProperty(prefix = "com.destiny.leopard", value = "enabled", havingValue = "true")
    public DestinyService starterService(){

        System.out.println(properties.getHobbyList());
        return new LeopardDestinyServiceImpl();
    }


}
