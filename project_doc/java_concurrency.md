1 为什么需要使用线程池
降低资源消耗 降低线程创建和销毁对系统资源的消耗
提高响应速度 当需要运行任务时直接运行，不需要等待线程创建
方便管理线程 对线程进行统一的分配、管理和监控

Executors.静态工厂类提供了 Executor ExecutorService ScheduleExecutorService ThreadFactory Callable 静态工厂方法

corePoolSize 核心线程数
maximumPoolSize 最大线程数
keepAliveTime 线程空闲时间
timeUnit 空闲时间单位
workQueue 等待队列  
  - ArrayBlockQueue   
  - LinkedBlockingQueue
  - SynchronousQueue
  - PriorityBlockQueue 

threadFactory 
DefaultThreadFactory 
自定义创建线程工厂

RejectHandler 

AbortPolicy 直接抛出异常
CallerRunsPolicy 使用调用者的线程来执行任务
DiscardOldestPolicy 丢弃最靠前的任务
DiscardPolicy 丢弃任务
  
  
1、在@Controller/@Service等容器中，默认情况下，scope值是单例-singleton的，也是线程不安全的。
2、尽量不要在@Controller/@Service等容器中定义静态变量，不论是单例(singleton)还是多实例(prototype)他都是线程不安全的。
3、默认注入的Bean对象，在不设置scope的时候他也是线程不安全的。
4、一定要定义变量的话，用ThreadLocal来封装，这个是线程安全的


