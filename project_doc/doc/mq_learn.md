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

