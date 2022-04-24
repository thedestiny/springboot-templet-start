//package com.destiny.origin.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * @Description
// * @Author liangwenchao
// * @Date 2022-03-16 12:34 PM
// */
//@Slf4j
//@Component
//public class AppBeanDefinitionConfig implements BeanDefinitionRegistryPostProcessor {
//
//    @Override
//    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        // invokeBeanFactoryPostProcessors 时机调用
//        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            // log.info("registry :: postProcessBeanDefinitionRegistry " + beanDefinitionName);
//        }
//    }
//
//    @Override
//    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        Iterator<String> iterator = beanFactory.getBeanNamesIterator();
//        while (iterator.hasNext()){
//            // log.info("factory :: postProcessBeanFactory " + iterator.next());
//        }
//    }
//}
