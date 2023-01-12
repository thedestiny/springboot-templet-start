package com.sk.manage.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-23 11:54 PM
 */

@Configuration
public class AppConfig {


    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher(){
        CredentialsMatcher credentialsMatcher = new AppCredentialsMatcher();
        return credentialsMatcher;
    }




}
