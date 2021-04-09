dubbo服务超时时间有xml和注解两种方式进行实现配置超时功能。在配置范围上分为全部超时配置、接口类上超时配置、以及接口方法上超时配置。
同类型上的配置消费端优先提供者端，靠近原则方法配置优先于接口类全局配置优先级最低。
所以dubbo的超时时间优先级为：消费者Method>提供者method>消费者Reference>提供者Service>消费者全局配置provider>提供者全局配置consumer。

dubbo.protocol.dubbo.payload=11557050（默认为8M，即8388608）

https://blog.csdn.net/icool_ali/article/details/80985130

dubbo 调用链
```
InvokerInvocationHandler
  -> AbstractClusterInvoker
    -> FailoverClusterInvoker 执行默认容错策略 是否配置有 mock=true MockClusterInvoker
       通过目录服务查找所有订阅的服务提供者,有 registryDirectory 和 static
       根据负载均衡 LoadBalance 来选取一个 invoker
     ->  Filter 调用多个过滤器 默认没有
       -> Invoker.inoker(invocation)


```


dubbo:reference中添加参数项mock=true
false，不调用mock服务。
true，当服务调用失败时，使用mock服务。
default，当服务调用失败时，使用mock服务。
force，强制使用Mock服务(不管服务能否调用成功)。(使用xml配置不生效,使用ReferenceConfigAPI可以生效)
