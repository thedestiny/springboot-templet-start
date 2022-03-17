package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.Iterator;

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
}
