package com.destiny.lobster.web;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@NacosPropertySource(dataId = "data-example", autoRefreshed = true, type = ConfigType.YAML)
@MapperScan("com.destiny.lobster.dao.mapper")
public class LobsterApplication {
	
	
	public static void main(String[] args) {
		
		log.info("start LobsterApplication !");
		SpringApplication.run(LobsterApplication.class, args);
	}
	
}
