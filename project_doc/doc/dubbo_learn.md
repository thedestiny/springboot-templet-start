dubbo服务超时时间有xml和注解两种方式进行实现配置超时功能。在配置范围上分为全部超时配置、接口类上超时配置、以及接口方法上超时配置。
同类型上的配置消费端优先提供者端，靠近原则方法配置优先于接口类全局配置优先级最低。
所以dubbo的超时时间优先级为：消费者Method>提供者method>消费者Reference>提供者Service>消费者全局配置provider>提供者全局配置consumer。
