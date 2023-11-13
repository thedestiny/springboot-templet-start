


```

feign 超时控制和 ribbon 超时配置

## 方法一：设置在ribbon上
ribbon:
  OkToRetryOnAllOperations: false #对所有操作请求都进行重试,默认false
  ReadTimeout: 5000   #负载均衡超时时间，默认值5000
  ConnectTimeout: 3000 #ribbon请求连接的超时时间，默认值2000
  MaxAutoRetries: 0     #对当前实例的重试次数，默认0
  MaxAutoRetriesNextServer: 1 #对切换实例的重试次数，默认1

## 方法二：设置在feign client上
feign:
  hystrix:
    enabled: false # 禁用 Hystrix 超时时间
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 3000
        
Hystrix 默认的超时时间是1秒，

# url 指定服务地址
# fallback 回调地址 ，fallback指定的类必须实现@FeignClient标记的接口
# configuration 日志级别配置 可以自定义Feign的Encoder、Decoder、LogLevel、Contract
# path 定义当前FeignClient的统一前缀
@FeignClient(
        name = "card",
        url = "http://localhost:7913",
        fallback = CardFeignClientFallback.class,
        configuration = FeignClientConfiguration.class
)

  <dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.0.0</version>
  </dependency>
   
        
```
