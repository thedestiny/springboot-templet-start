package com.destiny.origin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-03-16 12:34 PM
 */
@Slf4j
@Component
public class AppInstantiationAwareConfig implements InstantiationAwareBeanPostProcessor {
//    @Override
//    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
//        log.info("实例化之后 bean {}", beanName);
//        return false;
//    }
//    @Override
//    public Object postProcessBeforeInstantiation(Class<?> klass, String beanName) throws BeansException {
//        log.info("实例化之前 bean {} and klass {}", beanName, klass.getSimpleName());
//        if (klass == Student.class) {
//            log.info("klass {} beanName: {} 执行 post-process-before-instantiation 方法", klass.getSimpleName(), beanName);
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(klass);
//            enhancer.setCallback(new AppBeanMethodInterceptor());
//            return (Student) enhancer.create();
//        }
//        return null;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        log.info("初始化之后 bean {} and beanName {} ", beanName, bean);
//        return bean;
//    }
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        log.info("初始化之前 bean {}", beanName);
//        return bean;
//    }
//    @Override
//    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
//        log.info("进行数据性设置 bean {}", beanName);
//        return pvs;
//    }
}
