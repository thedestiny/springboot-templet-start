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
autoCreateTopicEnable=true
#是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
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
#- ASYNC_MASTER 异步复制Master
#- SYNC_MASTER 同步双写Master
#- SLAVE
brokerRole=ASYNC_MASTER
#刷盘方式
#- ASYNC_FLUSH 异步刷盘
#- SYNC_FLUSH 同步刷盘
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

