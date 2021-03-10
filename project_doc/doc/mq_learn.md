消息队列 RocketMQ 版既可为分布式
应用系统提供异步解耦和削峰填谷的能力，同时也具备互联网应用所需的海量消息堆
积、高吞吐、可靠重试等特性。

异步解耦 
消息堆积 高吞吐、可靠重试

topic 和 发布订阅
集群消费和广播消费

普通消息 顺序消息 事务消息 延迟、定时消息

name server 无状态节点可以集群部署，提供命名服务，更新和发现 broker
broker 消息中专角色，负责消息的存储和转发


消息的应用场景 
1 削峰填谷 
2 应用解耦
3 异步处理



顺序消息
1 全局顺序
2 分区顺序

成功、失败、超时

消息的类型
1 普通消息 对消费顺序无要求
2 定时消息
3 延时消息
4 全局顺序消息
5 分区顺序消息
6 事务消息


```bash

启动 mq nameserver 
.\bin\mqnamesrv.cmd
 
启动 mq borker 
.\bin\mqbroker.cmd -n localhost:9876 autoCreateTopicEnable=true
```


核心架构
producer nameServer broker consumer 

注册
通过tcp 场链接与broker 进行通信
broker 与每个nameserver 都建立长链接
心跳
broker 每30s 向nameServer 发送一次心跳 chm 进行维护broker 的注册
nameServer 每10s 进行一次心跳检查
120s没接到心跳就认为 broker 宕机了
数据同步
producer 从 nameserver 拉取 broker信息
主从同步 slave 从master 拉取数据
consumer 从broker 拉取数据消费


https://www.jianshu.com/p/4075c91cad66


pageCache + OS 异步刷盘+顺序读写

https://www.cnblogs.com/wuzm/p/11105176.html

mq 解耦异步削峰  降低可用性 一致性问题 复杂度提高

解决消息丢失的方案
发送端 同步发送 broker 持久化 消费者 关闭自动确认 at least once,消费端要保证幂等性

kafaka 
partition topic 的分区数和副本数
acks
retries


