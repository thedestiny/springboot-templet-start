package com.destiny.springbootes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = "com.destiny.springbootes.repository")
public class SpringbootEsApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringbootEsApplication.class);

    public static void main(String[] args) {


        SpringApplication.run(SpringbootEsApplication.class, args);
    }

}
