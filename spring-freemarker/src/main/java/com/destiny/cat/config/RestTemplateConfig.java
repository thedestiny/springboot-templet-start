package com.destiny.cat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;

/**
 * RestTemplate 配置
 *
 * @Description
 * @Date 2023-11-13 2:23 PM
 */

@Configuration
public class RestTemplateConfig {


    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {


        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {


        try {
            String userId = URLEncoder.encode("小明", "UTF-8");
        } catch (Exception e) {

        }
        // HttpComponentsAsyncClientHttpRequestFactory
        // MediaType.APPLICATION_JSON
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }

}
