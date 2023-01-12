package com.destiny.squirrel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description 跨域的四种设置方式
 * @Author destiny
 * @Date 2021-11-19 2:22 PM
 */

@Configuration
public class CrossConfig2  implements WebMvcConfigurer {

    // 实现 addCorsMappings 方法或者 使用 corsFilter

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }


    /**
     * 开启跨域访问拦截器
     */
    @Bean
    public CorsFilter corsFilter() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //设置放行哪些原始域
        corsConfiguration.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        corsConfiguration.addAllowedHeader("*");
        //放行哪些请求方式
        corsConfiguration.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //2. 添加映射路径
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
