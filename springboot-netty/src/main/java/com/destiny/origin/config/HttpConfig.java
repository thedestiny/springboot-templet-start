package com.destiny.origin.config;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-11-02 10:22 AM
 */
public class HttpConfig {

    private final static RestTemplate RT;

    static {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(60000);
        requestFactory.setReadTimeout(60000);
        RT = new RestTemplate(requestFactory);
        // 解决乱码问题
        RT.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }


    public static void send(){
//        RT.postForEntity("/api/", "", );
//        RT.exchange("",);

    }

}
