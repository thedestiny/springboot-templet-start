package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-03-16 12:34 PM
 */
@Slf4j
@Component
public class AppBeanDefinitionConfig implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("registry :: " + beanDefinitionName);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        Iterator<String> iterator = beanFactory.getBeanNamesIterator();
        while (iterator.hasNext()){
            System.out.println("factory :: " + iterator.next());
        }
    }

    public static void main(String[] args) {

        Map<String,String> he = new HashMap<>();
        he.put("name","123");
        Map<String,String> he1 = new HashMap<>();
        he1.putAll(he);
        he1.put("name","456");
        System.out.println(he);
        System.out.println(he1);


    }
}
