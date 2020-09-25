package com.destiny.wolf.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonConfig {
	
	
	@Value("${redisson.address}")
	private String addressUrl;
	
	@Value("${redisson.password}")
	private String password;
	
	
	@Bean
	public RedissonClient getRedisson() {
		Config config = new Config();
		config.useSingleServer()
				.setAddress(addressUrl)
				.setPassword(password)
				// .setReconnectionTimeout(10000)
				.setRetryInterval(5000)
				.setTimeout(10000)
				.setConnectTimeout(10000);
		return Redisson.create(config);
	}
	
	
}
