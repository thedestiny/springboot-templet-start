3 字符串操作 String StringBuilder StringBuffer
4 慎用正则表达式
5 collection ArrayList与LinkedList 
6 java8 中使用 stream 链式调用
7 hashmap 加载因子0.75 是空间利用率和查询时间成本的折中方案,数组+链表的方式解决存储和查询问题
8 网络通信优化之I/O模型 分为磁盘IO和网络IO 零拷贝
9 网络通信序列化 
10 网络通信通信协议 微服务的核心是远程通信和服务治理

1、微服务注册中心的注册表如何更好的防止读写并发冲突？
2、Nacos如何支撑阿里巴巴内部上百万服务实例的访问？
3、Nacos高并发异步注册架构知道如何设计的吗？
4、Eureka注册表多级缓存架构有了解过吗？
5、Sentinel底层滑动时间窗限流算法怎么实现的？
6、Sentinel底层是如何计算线上系统实时QPS的？
7、Seata分布式事务协调管理器是如何实现的？
8、Seata分布式事务一致性锁机制如何设计的？
9、Seata分布式事务回滚机制如何实现的？
10、Nacos集群CP架构底层类Raft协议怎么实现的？
11、Nacos&Eureka&Zookeeper集群架构都有脑裂问题吗？
12、如何设计能支撑全世界公司使用的微服务云架构？
13、RocketMQ架构如何设计能支撑每天万亿级消息处理？
14、RocketMQ在交易支付场景如何做到消息零丢失？

https://juejin.cn/post/6938924519793000456


```bash
# java_opts 配置
JAVA_OPTS='-Djava.security.egd=file:/dev/./urandom -server -Xms256m -Xmx496m -Dfile.encoding=UTF-8'

```
