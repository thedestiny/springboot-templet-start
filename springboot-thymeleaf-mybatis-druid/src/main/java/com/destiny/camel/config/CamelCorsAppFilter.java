package com.destiny.camel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.List;

/**
 
 跨域设置
 */
@Configuration
public class CamelCorsAppFilter extends CorsFilter {
 
	
	public CamelCorsAppFilter() {
        super(configSource());
    }

    /**
     * 跨域设置
     * @return UrlBasedCorsConfigurationSource
     */
    private static UrlBasedCorsConfigurationSource configSource() {
        List<String> allowedHeaders = new ArrayList<>();
        //增加自定义设置headers参数
        List<String> allowedMethods = new ArrayList<>();
        allowedMethods.add("OPTIONS");
        allowedMethods.add("POST");
        allowedMethods.add("GET");

        CorsConfiguration config = new CorsConfiguration();
        //默认不携带cookies
        config.setAllowCredentials(false);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.setAllowedHeaders(allowedHeaders);
        config.setAllowedMethods(allowedMethods);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
