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

1）、传入配置类，创建ioc容器
          2）、注册配置类，调用refresh（）刷新容器；
          3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建；
              1）、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
              2）、给容器中加别的BeanPostProcessor
              3）、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
              4）、再给容器中注册实现了Ordered接口的BeanPostProcessor；
              5）、注册没实现优先级接口的BeanPostProcessor；
              6）、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；
                  创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】
                  1）、创建Bean的实例
                  2）、populateBean；给bean的各种属性赋值
                  3）、initializeBean：初始化bean；
                          1）、invokeAwareMethods()：处理Aware接口的方法回调
                          2）、applyBeanPostProcessorsBeforeInitialization()：应用后置处理器的postProcessBeforeInitialization（）
                          3）、invokeInitMethods()；执行自定义的初始化方法
                          4）、applyBeanPostProcessorsAfterInitialization()；执行后置处理器的postProcessAfterInitialization（）；
                  4）、BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功；--》aspectJAdvisorsBuilder
              7）、把BeanPostProcessor注册到BeanFactory中；
                  beanFactory.addBeanPostProcessor(postProcessor);  
=======以上是创建和注册AnnotationAwareAspectJAutoProxyCreator的过程========
