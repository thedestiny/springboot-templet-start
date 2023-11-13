


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
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 3000
        
        
```
