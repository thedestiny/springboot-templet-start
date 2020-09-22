package com.destiny.wolf.config;


import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticProperties {
	
	
	/**
	 * 请求协议
	 */
	private String schema = "http";
	
	/**
	 * 集群名称
	 */
	private String clusterName = "elasticsearch";
	
	/**
	 * 集群节点
	 */
	// private List<String> clusterNodes = Lists.newArrayList("localhost:9200");
	/**
	 * 集群节点
	 */
	private List<String> endpoints = Lists.newArrayList("localhost:9200");
	
	/**
	 * 连接超时时间(毫秒)
	 */
	private Integer connectTimeout = 1000;
	
	/**
	 * socket 超时时间
	 */
	private Integer socketTimeout = 30000;
	
	/**
	 * 连接请求超时时间
	 */
	private Integer connectionRequestTimeout = 500;
	
	/**
	 * 每个路由的最大连接数量
	 */
	private Integer maxConnectPerRoute = 10;
	
	/**
	 * 最大连接总数量
	 */
	private Integer maxConnectTotal = 30;
	
	private Integer maxInMemorySize;
	
	
	/**
	 * 索引配置信息
	 */
	private Index index = new Index();
	
	
	/**
	 * 索引配置信息
	 */
	@Data
	public static class Index {
		
		/**
		 * 分片数量
		 */
		private Integer numberOfShards = 3;
		
		/**
		 * 副本数量
		 */
		private Integer numberOfReplicas = 1;
		
	}
	
	/**
	 * 认证用户
	 */
	private String username;
	
	/**
	 * 认证密码
	 */
	private String password;
	
	
}
