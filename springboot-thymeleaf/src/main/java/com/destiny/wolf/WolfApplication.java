package com.destiny.wolf;

import com.destiny.wolf.config.WolfAppEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableAsync
@EnableScheduling
@SpringBootApplication
// @EnableAspectJAutoProxy
@EnableElasticsearchRepositories(basePackages = "com.destiny.wolf.repository")
public class WolfApplication {
	
	public static void main(String[] args) {
		log.info("start WolfApplication !");
		ConfigurableApplicationContext run = SpringApplication.run(WolfApplication.class, args);
		WolfAppEvent event = new WolfAppEvent("test", "测试 App event");
		run.publishEvent(event);
	}
	
}
