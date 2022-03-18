https://www.jetbrains.com/shop/eform/opensource?product=ALL


```
springboot项目开发小技巧
https://www.cnblogs.com/gaomanito/p/11120164.html

https://www.jianshu.com/p/7122e6bef7f7
# 异步实现原理
https://www.cnblogs.com/shamo89/p/15715091.html


```


```
https://juejin.cn/post/7068837416714371102
订单超时未支付定时取消
1  quartz xxl-job 定时任务 轮询未支付的订单
2 jdk 延迟队列解决 DelayQueue
3 时间轮算法
4 redis zset  有序集合
添加元素:ZADD key score member [[score member] [score member] …]
按顺序查询元素:ZRANGE key start stop [WITHSCORES]
查询元素score:ZSCORE key member
移除元素:ZREM key member [member …]

5 消息队列 rabbitmq 
RabbitMQ可以针对Queue和Message设置 x-message-tt，来控制消息的生存时间，如果超时，则消息变为dead letter
lRabbitMQ的Queue可以配置x-dead-letter-exchange 和x-dead-letter-routing-key（可选）两个参数，用来控制队列内出现了deadletter，则按照这两个参数重新路由。结合以上两个特性，

Rocket MQ 队列
```