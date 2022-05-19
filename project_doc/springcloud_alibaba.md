

```

restful 
通讯协议是 http 性能比较低 灵活度高 应用在微服务框架
rpc 通讯协议是 tcp 性能较高 灵活度较高 soa 框架


网关
统一接入 安全防户 协议适配 流量管控 长短连接  容错能力

服务容错 

链路追踪
spanid traceid 

服务发现注册 配置中信 消息总线 负载均衡 锻炼器 数据监控


限流降级 
支持 WebServlet WebFlux OpenFeign RestTemplate SpringCloudGateway Zuul Dubbo RocketMQ 限流 Metrics 普罗米修斯

服务注册与发现 
springcloud  默认注册了 ribbon

配置中信 

消息驱动能力 springcloudstream 

分布式事务 Seta 

分布式调度任务 xxl-job 

组件 
Sentinel Nacos Rocketmq dubbo Seata springcloud acm oss sms 

springcloud version springcloud alibaba springboot version
greenwich                2.1.x                2.1.x.RELEASE

常见的注册中心 
zookeeper eureka consul nacos 

nacos 是注册中心也是配置中心 
DiscoveryClient 
http 调用 resttemplate 

服务之间的负载均衡 
服务端负载 nginx 和客户端负载 eureka 

springcloud ribbon 实现负载均衡  @LoadBalanced

IRule 

BestAvailableRule 最小的并发请求
RetryRule 重试策略
RoundRobinRule 轮询
RandomRule 随机

Feign 声明式的伪 http 客户端
feign 默认集成了 Ribbon 

Sentinel 服务容错

容错的手段一般有 
隔离 超时 限流 熔断 降级

隔离是线程池超时和信号量隔离

容错组件 
hystrix 
sentinel 
resilience4j 

sentinel 流控模式 

直接 当接口达到限流条件时开始限流 默认
关联 当关联的资源达到限流条件时，开始进行自我限流
链路 当从某个接口过来的资源达到限流条件时 开始限流 当入口资源进来的流量达到阈值，对指定资源进行限流


资源名即接口 uri
针对来源 default 针对微服务名进行限流
阈值类型 
qps 线程数类型 
 
流控效果 
快速失败 
warm up 冷热启动 几秒钟之内达到，避免流量激增
排队等待 均匀请求

降级规则 
rt response time 反应时间
异常比例 时间窗口 
异常数  

api 网关是指系统的统一入口
诸如 认证 鉴权 监控 路由转发 


nginx + lua 
kong 网关
zuul netflflix 开源网关
springcloud gateway  spring5.0 springboot 2.0 project reactor 

基于 netty 和 webflux 

springcloud:
  routes:
    id : 路由标识
    order : 优先级顺序
    predicates : 断言
    path: 指定路径进行转发
    uri: 转发地址 
    
    filters : 过滤器

gateway client 
http https websocket

globalFilter
gatewayFilter

链路追踪
sleuth 

cat 
zipkin
pinpoint 
skywalking
sleuth

mqnamesrv 

mqbroker






```