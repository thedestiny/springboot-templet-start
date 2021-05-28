package com.destiny.rabbit;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.destiny.rabbit.mapper")
public class RabbitApplication {
	
	/**
	 * PropertySourcesPlaceholderConfigurer  beanFactoryPostProcessor
	 * ConfigurationClassPostProcessor
	 *
	 * start {@link SpringBootApplication  } -> {@EnableAutoConfiguration }  ->
	 * AutoConfigurationImportSelector
	 *    ->  getCandidateConfigurations() -> getSpringFactoriesLoaderFactoryClass()
	 *  创建 SpringApplication() 对象 run
	 *  springboot  自动装配
	 *  getSpringFactoriesInstances 获取spring factories 实例
	 *  设置初始化器
	 *  setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
	 *             -> getSpringFactoriesInstances
	 *                 ->  SpringFactoriesLoader.loadFactoryNames -> loadSpringFactories
	 *                    ->  classLoader.getResources(FACTORIES_RESOURCE_LOCATION)
	 *                 ->  createSpringFactoriesInstances()
	 *
	 *  设置监听器
	 * 	setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
	 *
	 *  getSpringFactoriesInstances 进行获取
	 *
	 *  SpringApplication.run() 方法
	 *     -> SpringApplicationRunListeners listeners = getRunListeners(args); 获取监听器
	 *     -> prepareContext()
	 *        -> ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
	 *        -> Set<Object> sources = getAllSources(); 添加资源 资源是启动类
	 *        -> load(context, sources.toArray(new Object[0])); 加载主类
	 *             -> loader.load(); BeanDefinitionLoader.load
	 *                -> load  加载资源为 class package resource CharSequence
	 * 		  -> listeners.contextLoaded(context);
	 *     -> refreshContext
	 *        -> 最终会调用 AbstractApplicationContext.refresh() 方法
	 *          -> invokeBeanFactoryPostProcessors
	 *     -> afterRefresh
	 *
	 * AutoConfigurationImportSelector -> process -> getAutoConfigurationEntry -> getCandidateConfigurations -> SpringFactoriesLoader.loadFactoryNames
	 *
	 * load 方法
	 * import 标签解析
	 *
	 * refresh
	 *
	 * invokeBeanFactoryPostProcessor
	 *   -> ConfigurationClassParser -> process ->  ConfigurationClassPostProcessor
	 *
	 * onRefresh
	 *
	 * web server
	 *
	 * */
	/**
	 * AQS
	 * https://mp.weixin.qq.com/s/Vz3xLwKpodyU5RN9ERGgbA
	 * Synchronized
	 * https://mp.weixin.qq.com/s/0_5DLbBhA8MsgVAwXVv_Aw
	 *
	 * https://mp.weixin.qq.com/s/BAZ3jBxDR29tycDf9pCkBA
	 * https://mp.weixin.qq.com/s/2Eirpozx3HO0JHhhqWSTKg
	 * https://mp.weixin.qq.com/s/N9yqjWJVXX_5-V3-nbglBQ
	 * https://mp.weixin.qq.com/s/J-rbiYXGJNLPtkP98WyLBw
	 * https://mp.weixin.qq.com/s/XZPrqzbS-KVKKvYMNMz6sg
	 * */
	
	
	public static void main(String[] args) {
		
		log.info("start RabbitApplication !");
		
		// StandardEnvironment standardEnvironment =
		
		SpringApplication.run(RabbitApplication.class, args);

	}
	
}
