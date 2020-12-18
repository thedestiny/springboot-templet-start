```
IOC 

ClassPathXmlApplicationContext 调用 refresh

调用方法
AbstractApplicationContext.refresh()
                          -> finishBeanFactoryInitialization() -> preInstantiateSingletons()
DefaultListableBeanFactory.preInstantiateSingletons() -> getBean()
AbstractBeanFactory.getBean -> doGetBean() -> getSingleton()
                            -> doGetBean()
                            -> getSingleton(beanName, lambda) -> DefaultSingletonBeanRegistry.getSingleton() -> singletonFactory.getObject()
                            AbstractAutowireCapableBeanFactory.createBean()
                            AbstractAutowireCapableBeanFactory.doCreateBean()
                            AbstractAutowireCapableBeanFactory.createBeanInstance() -> instantiateBean()
                                                               ->  AbstractAutowireCapableBeanFactory.instantiateBean() -> getInstantiationStrategy().instantiate()
                            SimpleInstantiationStrategy.instantiate()   ->   BeanUtils.instantiateClass()
                            AbstractAutowireCapableBeanFactory.addSingletonFactory(beanName,lambda -> getEarlyBeanReference )
                            populateBean() -> applyPropertyValues()
                            AbstractAutowireCapableBeanFactory.applyPropertyValues() -> resolveValueIfNecessary()
                                         BeanDefinitionValueResolver.resolveValueIfNecessary(Object argName, @Nullable Object value) -> resolveReference()[RuntimeBeanReference]
                                         BeanDefinitionValueResolver.resolveReference(Object argName, RuntimeBeanReference ref)  -> bean = this.beanFactory.getBean(resolvedName) 获取 bean信息
                            bw.setPropertyValues(new MutablePropertyValues(deepCopy)); // 设置属性
 
                            initializeBean() 初始化 bean
                                       -> applyBeanPostProcessorsBeforeInitialization() // 后置处理器初始化前执行
                                       -> invokeInitMethods() // 初始化方法 
                                       -> applyBeanPostProcessorsAfterInitialization()  // 后置处理器初始化后执行
                                       
                            DefaultSingletonBeanRegistry.addSingleton()                  


```


```
AOP 

@EnableAspectJAutoProxy -> AspectJAutoProxyRegistrar
AspectJAutoProxyRegistrar implements ImportBeanDefinitionRegistrar

```

```


```

