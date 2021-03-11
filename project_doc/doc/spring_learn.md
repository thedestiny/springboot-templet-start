ClassPathXmlApplicationContext
loadBeanDefinition

AutoConfigurationImportSelector  spring boot 自动装配
AspectJAutoProxyBeanDefinitionParser aop 动态代理
AnnotationAwareAspectJAutoProxyCreator

BeanFactoryPostProcessor  -> BeanDefinitionRegistryPostProcessor
ConfigurableListableBeanFactory

线程的状态
new runnable blocked waiting  time_waiting terminated 
线程池的状态
running shutdown stop tidying terminated 

getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。 
getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。


PreparedStatement
Statement

XSS 跨站脚本攻击
CSRF 跨站请求伪造

OgnlCache 
https://blog.csdn.net/weixin_34082854/article/details/91518268

JVM 内存模型
jvm 内存结构


jmm 
我们把多个线程间通信的共享内存称之为主内存，而在并发编程中多个线程都维护了一个自己的本地内存（这是个抽象概念），其中保存的数据是主
内存中的数据拷贝。而JMM主要是控制本地内存和主内存之间的数据交互的。

java 对象模型
JVM内存结构，和Java虚拟机的运行时区域有关。Java内存模型，和Java的并发编程有关。Java对象模型，和Java对象在虚拟机中的表现形式有关。





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


getBean
ApplicationContext 
AdvisedSupport
AopConfig 
Advice
JdkDynamicAopProxy
CglibDynamicAopProxy


DefaultSingletonBeanRegistry

https://www.processon.com/view/5fa5ced3e0b34d7a1a9b77bc

```

The root interface for accessing a Spring bean container.
This is the basic client view of a bean container; further interfaces such as ListableBeanFactory and org.springframework.beans.factory.config.ConfigurableBeanFactory are available for specific purposes.
This interface is implemented by objects that hold a number of bean definitions, each uniquely identified by a String name. Depending on the bean definition, the factory will return either an independent instance of a contained object (the Prototype design pattern), or a single shared instance (a superior alternative to the Singleton design pattern, in which the instance is a singleton in the scope of the factory). Which type of instance will be returned depends on the bean factory configuration: the API is the same. Since Spring 2.0, further scopes are available depending on the concrete application context (e.g. "request" and "session" scopes in a web environment).
The point of this approach is that the BeanFactory is a central registry of application components, and centralizes configuration of application components (no more do individual objects need to read properties files, for example). See chapters 4 and 11 of "Expert One-on-One J2EE Design and Development" for a discussion of the benefits of this approach.
Note that it is generally better to rely on Dependency Injection ("push" configuration) to configure application objects through setters or constructors, rather than use any form of "pull" configuration like a BeanFactory lookup. Spring's Dependency Injection functionality is implemented using this BeanFactory interface and its subinterfaces.
Normally a BeanFactory will load bean definitions stored in a configuration source (such as an XML document), and use the org.springframework.beans package to configure the beans. However, an implementation could simply return Java objects it creates as necessary directly in Java code. There are no constraints on how the definitions could be stored: LDAP, RDBMS, XML, properties file, etc. Implementations are encouraged to support references amongst beans (Dependency Injection).
In contrast to the methods in ListableBeanFactory, all of the operations in this interface will also check parent factories if this is a HierarchicalBeanFactory. If a bean is not found in this factory instance, the immediate parent factory will be asked. Beans in this factory instance are supposed to override beans of the same name in any parent factory.
Bean factory implementations should support the standard bean lifecycle interfaces as far as possible. The full set of initialization methods and their standard order is:
BeanNameAware's setBeanName
BeanClassLoaderAware's setBeanClassLoader
BeanFactoryAware's setBeanFactory
EnvironmentAware's setEnvironment
EmbeddedValueResolverAware's setEmbeddedValueResolver
ResourceLoaderAware's setResourceLoader (only applicable when running in an application context)
ApplicationEventPublisherAware's setApplicationEventPublisher (only applicable when running in an application context)
MessageSourceAware's setMessageSource (only applicable when running in an application context)
ApplicationContextAware's setApplicationContext (only applicable when running in an application context)
ServletContextAware's setServletContext (only applicable when running in a web application context)
postProcessBeforeInitialization methods of BeanPostProcessors
InitializingBean's afterPropertiesSet
a custom init-method definition
postProcessAfterInitialization methods of BeanPostProcessors
On shutdown of a bean factory, the following lifecycle methods apply:
postProcessBeforeDestruction methods of DestructionAwareBeanPostProcessors
DisposableBean's destroy
a custom destroy-method definition
Since:
13 April 2001
See Also:
BeanNameAware.setBeanName, BeanClassLoaderAware.setBeanClassLoader, BeanFactoryAware.setBeanFactory, org.springframework.context.ResourceLoaderAware.setResourceLoader, org.springframework.context.ApplicationEventPublisherAware.setApplicationEventPublisher, org.springframework.context.MessageSourceAware.setMessageSource, org.springframework.context.ApplicationContextAware.setApplicationContext, org.springframework.web.context.ServletContextAware.setServletContext, org.springframework.beans.factory.config.BeanPostProcessor.postProcessBeforeInitialization, InitializingBean.afterPropertiesSet, org.springframework.beans.factory.support.RootBeanDefinition.getInitMethodName, org.springframework.beans.factory.config.BeanPostProcessor.postProcessAfterInitialization, DisposableBean.destroy, org.springframework.beans.factory.support.RootBeanDefinition.getDestroyMethodName
Author:
Rod Johnson, Juergen Hoeller, Chris Beams
