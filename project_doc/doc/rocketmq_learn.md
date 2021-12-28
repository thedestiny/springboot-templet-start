https://blog.csdn.net/leexide/article/details/80035470

rocketmq 
##### 1 参数配置
```
#所属集群名字
brokerClusterName=rocketmq-cluster

#broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-a|broker-b

#0表示Master，>0表示Slave
brokerId=0

#nameServer地址，分号分割
namesrvAddr=192.168.1.101:9876;192.168.1.102:9876

#在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4

#是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=false

#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=false

#Broker 对外服务的监听端口
listenPort=10911

#删除文件时间点，默认凌晨 4点
deleteWhen=04

#文件保留时间，默认 48 小时
fileReservedTime=120

#commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824

#ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000

#destroyMapedFileIntervalForcibly=120000

#redeleteHangedFileInterval=120000

#检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88

#存储路径
storePathRootDir=/usr/local/alibaba-rocketmq/store

#commitLog 存储路径
storePathCommitLog=/usr/local/alibaba-rocketmq/store/commitlog

#消费队列存储路径存储路径
storePathConsumeQueue=/usr/local/alibaba-rocketmq/store/consumequeue

#消息索引存储路径
storePathIndex=/usr/local/alibaba-rocketmq/store/index

#checkpoint 文件存储路径
storeCheckpoint=/usr/local/alibaba-rocketmq/store/checkpoint

#abort 文件存储路径
abortFile=/usr/local/alibaba-rocketmq/store/abort

#限制的消息大小
maxMessageSize=65536

#flushCommitLogLeastPages=4
#flushConsumeQueueLeastPages=2
#flushCommitLogThoroughInterval=10000
#flushConsumeQueueThoroughInterval=60000

#Broker 的角色
# ASYNC_MASTER 主从异步复制Master SYNC_MASTER 同步双写Master SLAVE
brokerRole=ASYNC_MASTER

#刷盘方式
#- ASYNC_FLUSH 异步刷盘 SYNC_FLUSH  同步刷盘
flushDiskType=ASYNC_FLUSH

#checkTransactionMessageEnable=false

#发消息线程池数量
#sendMessageThreadPoolNums=128

#拉消息线程池数量
#pullMessageThreadPoolNums=128


```

windows 启动
```
修改 runserver.cmd mqbroker.cmd tools.cmd 内存配置大小
start mqnamesrv.cmd
start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true



```



```
消息类型  普通消息 顺序消息 事务消息
发送方式  同步发送 异步发送 单向发送

DefaultMQProducer 和 TransactionMQProducer
于生产普通消息、顺序消息(分区有序和全局有序)、单向消息、批量消息、延迟消息，后者主要用于生产事务消息

1 客户端重试机制 retryTimesWhenSendFailed
2 broker-发送延迟机制 发送延迟容错开关 sendLatencyFaultEnable 默认为关闭的状态
3 broker 保证  同步复制和异步复制

DefaultMQProducer 是生产者的默认实现模式

延迟消息实现类 ScheduleMessageService
# 设置消息延迟级别
setDelatTimeLevel()

sendOneway 发送单向消息

批量发送 
消息最好小于1MB 
同一批批量消息 tpoic waitStoreMsg 属性必须一致
批量消息不支持延迟消息


集群消费
广播消费
可靠消费
重试-死信机制 重试16次  RETRY消费者组  DLQ消费者组



```


#######  消息的过滤
```
tag 过滤 
字符串比较的速度相较Hashcode慢，在broker端采用hashcode而在客户端采用字符串比较。
hashcode 是数字使用位运算而字符串比较则需要按照顺序进行比较
sql 过滤 
Bloom过滤器 做第一次过滤
sql 编译后 evaluate 作第二次过滤
启用sql 过滤需要开启一下配置
enableConsumeQueueExt = true
filterSupportRetry = true 
enablePropertyFilter = true 
enableCalcFilterBitMap = true 

filter server 过滤的方式

fliterServerNums = 大于0的数


消费者不能消费消息是最常见的问题之一，也是每个消息队列服务都会遇到的问题

同步刷盘 异步刷盘 
同步复制 异步复制 

复制是指Broker与Broker之间的数据同步方式

刷盘是指数据发送到Broker的内存（通常指PageCache）后，以何种方式持久化到磁盘

> /dev/null 2>&1 &

namersrv s是针对rocketmq开发的轻量级协调者，多个节点组件集群，帮助rocketmq达到高可用
namesrc 主要功能是临时保存、管理topic路由信息，各个namesrv 是无状态的，节点之间不通信
broker producer consumer 启动时轮询全部配置的namesrv节点，拉取路由信息



```

###### broker 消息存储
```
bbroker 目录存储结构
broker 
     - consumequeue 
          - topic 
             - 0 
                0000001
             - 1 
                0000002
     - commitlog
          - 00010001
          - 00020001
     - index 
          - 00000001
          - 00001001             
     - config 
       - consumerOffset.json
       - delayOffset.json
       - consumerFilter.json
       - topics.json
       - subscriptionGroup.json
     - abort
     - lock
     - checkpoint
       
commitlog 是一个目录，其中包含具体的存储文件，文件名长度为20个字符，文件名包含保存消息的最大物理offset值，最高位补0
每个文件的大小为1GB,可以通过 mappedFileSizeCommitLog 进行配置

consumequeue 是一个目录，包含盖broker上所有的topic对应的消费队列文件信息。消费队列的文件格式为
./consumequeue/topName名/queueId/具体的消费文件，每个消费队列其实是 commitlog的一个索引，提供给消费者进行拉取更新点位使用

index 是一个目录，文件是按照消息的key创建的hash索引文件，文件名采用时间戳

config 这是一个目录。保存了当前broker的 topic 订阅关系和消费进度，数据会持久化磁盘

abort broker 异常关闭标识。正常关闭会删除，异常则不会，broker 会根据标记决定是否需要重新构建 index 索引

checkpoint  broker 最近一次正常运行时的状态，最后一次刷盘、创建索引时间


java nio 
全部内存=物理内存+虚拟内存
操作系统Page Cache机制（页缓存机制）

rocketmq 存储机制 文件映射和顺序写 数据刷盘

Broker通过 CommitLog、ConsumeQueue、IndexFile等来组织存储消息

Page Cache：现代操作系统内核被设计为按照Page读取文件，每个Page默认为4KB



```

