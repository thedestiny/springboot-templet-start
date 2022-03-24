
##### 给容器中注册组件
```
aop 

给容器中注册组件；
1）、包扫描+组件标注注解（@Controller/@Service/@Repository/@Component）[自己写的类]
2）、@Bean[导入的第三方包里面的组件]
3）、@Import[快速给容器中导入一个组件]
		1）、@Import(要导入到容器中的组件)；容器中就会自动注册这个组件，id默认是全类名
		2）、ImportSelector:返回需要导入的组件的全类名数组；
		3）、ImportBeanDefinitionRegistrar:手动注册bean到容器中
4）、使用Spring提供的 FactoryBean（工厂Bean）;
		1）、默认获取到的是工厂bean调用getObject创建的对象
		2）、要获取工厂Bean本身，我们需要给id前面加一个&
			&colorFactoryBean
```

##### AOP动态代理
```
 AOP：【动态代理】
 		指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式；
 
 1、导入aop模块；Spring AOP：(spring-aspects)
 2、定义一个业务逻辑类（MathCalculator）；在业务逻辑运行的时候将日志进行打印（方法之前、方法运行结束、方法出现异常，xxx）
 3、定义一个日志切面类（LogAspects）：切面类里面的方法需要动态感知MathCalculator.div运行到哪里然后执行；
 		通知方法：
 			前置通知(@Before)：logStart：在目标方法(div)运行之前运行
 			后置通知(@After)：logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
 			返回通知(@AfterReturning)：logReturn：在目标方法(div)正常返回之后运行
 			异常通知(@AfterThrowing)：logException：在目标方法(div)出现异常以后运行
 			环绕通知(@Around)：动态代理，手动推进目标方法运行（joinPoint.procced()）
 4、给切面类的目标方法标注何时何地运行（通知注解）；
 5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中;
 6、必须告诉Spring哪个类是切面类(给切面类上加一个注解：@Aspect)
 [7]、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
 		在Spring中很多的 @EnableXXX;
 
 三步：
 	1）、将业务逻辑组件和切面类都加入到容器中；告诉Spring哪个是切面类（@Aspect）
 	2）、在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
    3）、开启基于注解的aop模式；@EnableAspectJAutoProxy
  
 AOP原理：【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
 		@EnableAspectJAutoProxy
 1、@EnableAspectJAutoProxy是什么？
 		@Import(AspectJAutoProxyRegistrar.class)：给容器中导入AspectJAutoProxyRegistrar
 			利用AspectJAutoProxyRegistrar自定义给容器中注册bean；BeanDefinetion
 			internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 
 		给容器中注册一个AnnotationAwareAspectJAutoProxyCreator；
 
 2、 AnnotationAwareAspectJAutoProxyCreator：
 		AnnotationAwareAspectJAutoProxyCreator
 			->AspectJAwareAdvisorAutoProxyCreator
 				->AbstractAdvisorAutoProxyCreator
 					->AbstractAutoProxyCreator
 							implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 						关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
 
 AbstractAutoProxyCreator.setBeanFactory()
 AbstractAutoProxyCreator.有后置处理器的逻辑；
 AbstractAdvisorAutoProxyCreator.setBeanFactory() -> initBeanFactory()
 AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 
 流程：
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
 
 			AnnotationAwareAspectJAutoProxyCreator => InstantiationAwareBeanPostProcessor
 		4）、finishBeanFactoryInitialization(beanFactory);完成BeanFactory初始化工作；创建剩下的单实例bean
 			1）、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
 				getBean->doGetBean()->getSingleton()->
 			2）、创建bean
 				【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor，
 				会调用postProcessBeforeInstantiation()】
 				1）、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
 					只要创建好的Bean都会被缓存起来
 				2）、createBean（）;创建bean；
 					AnnotationAwareAspectJAutoProxyCreator 会在任何bean创建之前先尝试返回bean的实例
 					【BeanPostProcessor是在Bean对象创建完成初始化前后调用的】
 					【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象的】
 					1）、resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
 						希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
 						1）、后置处理器先尝试返回对象；
 							bean = applyBeanPostProcessorsBeforeInstantiation（）：
 								拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor;
 								就执行postProcessBeforeInstantiation
 							if (bean != null) {
								bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
							}
 
 					2）、doCreateBean(beanName, mbdToUse, args);真正的去创建一个bean实例；和3.6流程一样；
 					3）、
 			
 		
 AnnotationAwareAspectJAutoProxyCreator【InstantiationAwareBeanPostProcessor】	的作用：
 1）、每一个bean创建之前，调用postProcessBeforeInstantiation()；
 		关心MathCalculator和LogAspect的创建
 		1）、判断当前bean是否在advisedBeans中（保存了所有需要增强bean）
 		2）、判断当前bean是否是基础类型的Advice、Pointcut、Advisor、AopInfrastructureBean，
 			或者是否是切面（@Aspect）
 		3）、是否需要跳过
 			1）、获取候选的增强器（切面里面的通知方法）【List<Advisor> candidateAdvisors】
 				每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor；
 				判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
 			2）、永远返回false
 
 2）、创建对象
 postProcessAfterInitialization；
 		return wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下
 		1）、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
 			1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
 			2、获取到能在bean使用的增强器。
 			3、给增强器排序
 		2）、保存当前bean在advisedBeans中；
 		3）、如果当前bean需要增强，创建当前bean的代理对象；
 			1）、获取所有增强器（通知方法）
 			2）、保存到proxyFactory
 			3）、创建代理对象：Spring自动决定
 				JdkDynamicAopProxy(config);jdk动态代理；
 				ObjenesisCglibAopProxy(config);cglib的动态代理；
 		4）、给容器中返回当前组件使用cglib增强了的代理对象；
 		5）、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
 		
 	
 	3）、目标方法执行	；
 		容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器，目标对象，xxx）；
 		1）、CglibAopProxy.intercept();拦截目标方法的执行
 		2）、根据ProxyFactory对象获取将要执行的目标方法拦截器链；
 			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 			1）、List<Object> interceptorList保存所有拦截器 5
 				一个默认的ExposeInvocationInterceptor 和 4个增强器；
 			2）、遍历所有的增强器，将其转为Interceptor；
 				registry.getInterceptors(advisor);
 			3）、将增强器转为List<MethodInterceptor>；
 				如果是MethodInterceptor，直接加入到集合中
 				如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor；
 				转换完成返回MethodInterceptor数组；
 
 		3）、如果没有拦截器链，直接执行目标方法;
 			拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
 		4）、如果有拦截器链，把需要执行的目标对象，目标方法，
 			拦截器链等信息传入创建一个 CglibMethodInvocation 对象，
 			并调用 Object retVal =  mi.proceed();
 		5）、拦截器链的触发过程;
 			1)、如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
 			2)、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
 				拦截器链的机制，保证通知方法与目标方法的执行顺序；
 		
 	总结：
 		1）、  @EnableAspectJAutoProxy 开启AOP功能
 		2）、 @EnableAspectJAutoProxy 会给容器中注册一个组件 AnnotationAwareAspectJAutoProxyCreator
 		3）、AnnotationAwareAspectJAutoProxyCreator是一个后置处理器；
 		4）、容器的创建流程：
 			1）、registerBeanPostProcessors（）注册后置处理器；创建AnnotationAwareAspectJAutoProxyCreator对象
 			2）、finishBeanFactoryInitialization（）初始化剩下的单实例bean
 				1）、创建业务逻辑组件和切面组件
 				2）、AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程
 				3）、组件创建完之后，判断组件是否需要增强
 					是：切面的通知方法，包装成增强器（Advisor）;给业务逻辑组件创建一个代理对象（cglib）；
 		5）、执行目标方法：
 			1）、代理对象执行目标方法
 			2）、CglibAopProxy.intercept()；
 				1）、得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）
 				2）、利用拦截器的链式机制，依次进入每一个拦截器进行执行；
 				3）、效果：
 					正常执行：前置通知-》目标方法-》后置通知-》返回通知
 					出现异常：前置通知-》目标方法-》后置通知-》异常通知

```

##### bean的生命周期

```

 bean的生命周期：
 		bean创建---初始化----销毁的过程
 容器管理bean的生命周期；
 我们可以自定义初始化和销毁方法；容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 
 构造（对象创建）
 		单实例：在容器启动的时候创建对象
 		多实例：在每次获取的时候创建对象\
 
 BeanPostProcessor.postProcessBeforeInitialization
 初始化：
 		对象创建完成，并赋值好，调用初始化方法。。。
 BeanPostProcessor.postProcessAfterInitialization
 销毁：
 		单实例：容器关闭的时候
 		多实例：容器不会管理这个bean；容器不会调用销毁方法；
 
 
 遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization，
 一但返回null，跳出for循环，不会执行后面的BeanPostProcessor.postProcessorsBeforeInitialization
 
 BeanPostProcessor原理
 populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
 initializeBean
 {
 applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 invokeInitMethods(beanName, wrappedBean, mbd);执行自定义初始化
 applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
}
 
 
 
 1）、指定初始化和销毁方法；
 		通过@Bean指定init-method和destroy-method；
 2）、通过让Bean实现InitializingBean（定义初始化逻辑），
 				DisposableBean（定义销毁逻辑）;
 3）、可以使用JSR250；
 		@PostConstruct：在bean创建完成并且属性赋值完成；来执行初始化方法
 		@PreDestroy：在容器销毁bean之前通知我们进行清理工作
 4）、BeanPostProcessor【interface】：bean的后置处理器；
 		在bean初始化前后进行一些处理工作；
 		postProcessBeforeInitialization:在初始化之前工作
 		postProcessAfterInitialization:在初始化之后工作
 
 Spring底层对 BeanPostProcessor 的使用；
 		bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async,xxx BeanPostProcessor;


```

##### 自动装配
```

自动装配;
		Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值；

1）、@Autowired：自动注入：
		1）、默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);找到就赋值
		2）、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
							applicationContext.getBean("bookDao")
		3）、@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
		4）、自动装配默认一定要将属性赋值好，没有就会报错；
			可以使用@Autowired(required=false);
		5）、@Primary：让Spring进行自动装配的时候，默认使用首选的bean；
				也可以继续使用@Qualifier指定需要装配的bean的名字
		BookService{
			@Autowired
			BookDao  bookDao;
		}

2）、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
		@Resource:
			可以和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的；
			没有能支持@Primary功能没有支持@Autowired（reqiured=false）;
		@Inject:
			需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能；
 @Autowired:Spring定义的； @Resource、@Inject都是java规范
	
AutowiredAnnotationBeanPostProcessor:解析完成自动装配功能；		

3）、 @Autowired:构造器，参数，方法，属性；都是从容器中获取参数组件的值
		1）、[标注在方法位置]：@Bean+方法参数；参数从容器中获取;默认不写@Autowired效果是一样的；都能自动装配
		2）、[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
		3）、放在参数位置：

4）、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext，BeanFactory，xxx）；
		自定义组件实现xxxAware；在创建对象的时候，会调用接口规定的方法注入相关组件；Aware；
		把Spring底层一些组件注入到自定义的Bean中；
		xxxAware：功能使用xxxProcessor；
			ApplicationContextAware==》ApplicationContextAwareProcessor；


```


##### 扩展原理
```
 扩展原理：
 BeanPostProcessor：bean后置处理器，bean创建对象初始化前后进行拦截工作的
 
 1、BeanFactoryPostProcessor：beanFactory的后置处理器；
 		在BeanFactory标准初始化之后调用，来定制和修改BeanFactory的内容；
 		所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建
 
 
 BeanFactoryPostProcessor原理:
 1)、ioc容器创建对象
 2)、invokeBeanFactoryPostProcessors(beanFactory);
 		如何找到所有的BeanFactoryPostProcessor并执行他们的方法；
 			1）、直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
 			2）、在初始化创建其他组件前面执行
 
 2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 		postProcessBeanDefinitionRegistry();
 		在所有bean定义信息将要被加载，bean实例还未创建的；
 
 		优先于BeanFactoryPostProcessor执行；
 		利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件；
 
 	原理：
 		1）、ioc创建对象
 		2）、refresh()-》invokeBeanFactoryPostProcessors(beanFactory);
 		3）、从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
 			1、依次触发所有的postProcessBeanDefinitionRegistry()方法
 			2、再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor；
 
 		4）、再来从容器中找到BeanFactoryPostProcessor组件；然后依次触发postProcessBeanFactory()方法
 	
 3、ApplicationListener：监听容器中发布的事件。事件驱动模型开发；
 	  public interface ApplicationListener<E extends ApplicationEvent>
 		监听 ApplicationEvent 及其下面的子事件；
 
 	 步骤：
 		1）、写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）
 			@EventListener;
 			原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；
 
 		2）、把监听器加入到容器；
 		3）、只要容器中有相关事件的发布，我们就能监听到这个事件；
 				ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
 				ContextClosedEvent：关闭容器会发布这个事件；
 		4）、发布一个事件：
 				applicationContext.publishEvent()；
 	
  原理：
  	ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的时间]、ContextClosedEvent；
  1）、ContextRefreshedEvent事件：
  	1）、容器创建对象：refresh()；
  	2）、finishRefresh();容器刷新完成会发布ContextRefreshedEvent事件
  2）、自己发布事件；
  3）、容器关闭会发布ContextClosedEvent；
  
  【事件发布流程】：
  	3）、publishEvent(new ContextRefreshedEvent(this));
  			1）、获取事件的多播器（派发器）：getApplicationEventMulticaster()
  			2）、multicastEvent派发事件：
  			3）、获取到所有的ApplicationListener；
  				for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
  				1）、如果有Executor，可以支持使用Executor进行异步派发；
  					Executor executor = getTaskExecutor();
  				2）、否则，同步的方式直接执行listener方法；invokeListener(listener, event);
  				 拿到listener回调onApplicationEvent方法；
  
  【事件多播器（派发器）】
  	1）、容器创建对象：refresh();
  	2）、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
  		1）、先去容器中找有没有id=“applicationEventMulticaster”的组件；
  		2）、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
  			并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster；
  
  【容器中有哪些监听器】
  	1）、容器创建对象：refresh();
  	2）、registerListeners();
  		从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
  		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
  		//将listener注册到ApplicationEventMulticaster中
  		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
  		
   SmartInitializingSingleton 原理：->afterSingletonsInstantiated();
   		1）、ioc容器创建对象并refresh()；
   		2）、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
   			1）、先创建所有的单实例bean；getBean();
   			2）、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的；
   				如果是就调用afterSingletonsInstantiated();

```

##### 声明式事务
```

环境搭建：
1、导入相关依赖
		数据源、数据库驱动、Spring-jdbc模块
2、配置数据源、JdbcTemplate（Spring提供的简化数据库操作的工具）操作数据
3、给方法上标注 @Transactional 表示当前方法是一个事务方法；
4、 @EnableTransactionManagement 开启基于注解的事务管理功能；
		@EnableXXX
5、配置事务管理器来控制事务;
		@Bean
		public PlatformTransactionManager transactionManager()


原理：
1）、@EnableTransactionManagement
			利用TransactionManagementConfigurationSelector给容器中会导入组件
			导入两个组件
			AutoProxyRegistrar
			ProxyTransactionManagementConfiguration
2）、AutoProxyRegistrar：
			给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
			InfrastructureAdvisorAutoProxyCreator：？
			利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；

3）、ProxyTransactionManagementConfiguration 做了什么？
			1、给容器中注册事务增强器；
				1）、事务增强器要用事务注解的信息，AnnotationTransactionAttributeSource解析事务注解
				2）、事务拦截器：
					TransactionInterceptor；保存了事务属性信息，事务管理器；
					他是一个 MethodInterceptor；
					在目标方法执行的时候；
						执行拦截器链；
						事务拦截器：
							1）、先获取事务相关的属性
							2）、再获取PlatformTransactionManager，如果事先没有添加指定任何transactionmanger
								最终会从容器中按照类型获取一个PlatformTransactionManager；
							3）、执行目标方法
								如果异常，获取到事务管理器，利用事务管理回滚操作；
								如果正常，利用事务管理器，提交事务
```

```

1、图解spring IOC容器的核心实现原理
2、BeanFactoryPostProcessor接口详解
3、BeanPostProcessor接口详解
4、必知必会的13个Bean生命周期处理机制
5、Spring Bean实现Aware接口的意义
6、BeanFactory和FactoryBean的接口对比和实现

```


```

https://blog.csdn.net/qiesheng/article/details/72875315

```


```
BeanFactory 是一个Bean工厂,定义了IOC容器的最基本形式，并提供了IOC容器应遵守的的最基本的接口，也就是Spring IOC所遵守的最底层和最基本的编程规范。
在Spring代码中，BeanFactory只是个接口，并不是IOC容器的具体实现，但是Spring容器给出了很多种实现，如 、XmlBeanFactory、等，都是附加了某种功能的实现。

Spring通过反射机制利用<bean>的class属性指定实现类实例化Bean，在某些情况下，实例化Bean过程比较复杂，如果按照传统的方式，则需要在<bean>中提供大量的配置信息。配置方式的灵活性是受限的，这时采用编码的方式可能会得到一个简单的方案。Spring为此提供了一个org.springframework.bean.factory.FactoryBean的工厂类接口，用户可以通过实现该接口定制实例化Bean的逻辑。
FactoryBean接口对于Spring框架来说占用重要的地位，Spring自身就提供了70多个FactoryBean的实现。它们隐藏了实例化一些复杂Bean的细节，给上层应用带来了便利。从Spring3.0开始，FactoryBean开始支持泛型，即接口声明改为FactoryBean<T>的形式


BeanFactory是个Factory，也就是IOC容器或对象工厂，FactoryBean是个Bean。在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的。但对FactoryBean而言，这个Bean不是简单的Bean，而是一个能生产或者修饰对象生成的工厂Bean,它的实现与设计模式中的工厂模式和修饰器模式类似。


在Spring中，所有的Bean都是由BeanFactory(也就是IOC容器)来进行管理的。BeanFactory定义了容器的基本形式，并规定了IOC容器的基本接口以及生命周期，针对 BeanFactory 的实现类也有很多，ApplicationContext，DefaultListableBeanFactory，XmlBeanFactory 都是其添加了附加功能的接口。BeanFactory 创建的 Bean对象需要遵循以下的生命周期形式，并且Bean 的生产都是通过反射机制来实现的。但是在某些具体的情况下，实例化Bean 的操作会很复杂，按照其要求需要配置大量的属性，此时Bean的配置灵活性就受到了限制，此时就使用到了FactoryBean的工厂类接口，该接口可以按照用户的需求来构造 Bean 对象，而不需要




实例化 Instantiation
属性赋值 Populate
初始化 Initialization
销毁 Destruction

https://blog.csdn.net/mamamalululu00000000/article/details/107233549
https://blog.csdn.net/qq_38526573/article/details/88091702


```
