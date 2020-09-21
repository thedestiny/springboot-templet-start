package com.destiny.wolf.config;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
public class ElasticSearchConfig {
	
	@Autowired
	private ElasticProperties properties;
	
	
	@Bean
	@ConditionalOnMissingBean
	public RestHighLevelClient restHighLevelClient() {
		List<String> clusterNodes = properties.getEndpoints();
		if (clusterNodes.isEmpty()) {
			throw new RuntimeException("集群节点不允许为空");
		}
		
		List<HttpHost> httpHosts = new ArrayList<>();
		clusterNodes.forEach(node -> {
			try {
				String[] parts = StrUtil.split(node, ":");
				Assert.notNull(parts, "Must defined");
				Assert.state(parts.length == 2, "Must be defined as 'host:port'");
				httpHosts.add(new HttpHost(parts[0], Integer.parseInt(parts[1]), properties.getSchema()));
			} catch (Exception e) {
				throw new IllegalStateException("Invalid ES nodes " + "property '" + node + "'", e);
			}
		});
		RestClientBuilder builder = RestClient.builder(httpHosts.toArray(new HttpHost[0]));
		
		return getRestHighLevelClient(builder, properties);
	}
	
	/**
	 * get restHistLevelClient
	 *
	 * @return
	 */
	private static RestHighLevelClient getRestHighLevelClient(RestClientBuilder builder, ElasticProperties properties) {
		// Callback used the default {@link RequestConfig} being set to the {@link CloseableHttpClient}
		
		builder.setRequestConfigCallback(requestConfigBuilder -> {
			requestConfigBuilder.setConnectTimeout(properties.getConnectTimeout());
			requestConfigBuilder.setSocketTimeout(properties.getSocketTimeout());
			requestConfigBuilder.setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
			return requestConfigBuilder;
		});
		
		// Callback used to customize the {@link CloseableHttpClient} instance used by a {@link RestClient} instance.
		builder.setHttpClientConfigCallback(httpClientBuilder -> {
			httpClientBuilder.setMaxConnTotal(properties.getMaxConnectTotal());
			httpClientBuilder.setMaxConnPerRoute(properties.getMaxConnectPerRoute());
			return httpClientBuilder;
		});
		
		
		// Callback used the basic credential auth
		
		if (!StrUtil.isEmpty(properties.getUsername()) && !StrUtil.isEmpty(properties.getUsername())) {
			final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getUsername(), properties.getPassword()));
			
			builder.setHttpClientConfigCallback( (client) -> {
				
				return client.setDefaultCredentialsProvider(credentialsProvider);
			});
		}
		
		
		return new RestHighLevelClient(builder);
	}
	
}
