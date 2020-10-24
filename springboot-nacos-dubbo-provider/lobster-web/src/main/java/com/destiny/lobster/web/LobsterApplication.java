package com.destiny.lobster.web;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@EnableDubbo
@SpringBootApplication
// 配置文件中配置 nacos.config
@NacosPropertySource(dataId = "data-example", autoRefreshed = true, type = ConfigType.YAML)
@MapperScan("com.destiny.lobster.dao")
@ComponentScan(value = "com.destiny.lobster")
public class LobsterApplication {
	
	
	public static void main(String[] args) {
		
		log.info("start LobsterApplication !");
		ConfigurableApplicationContext run = SpringApplication.run(LobsterApplication.class, args);
		
		
	}
	
}
