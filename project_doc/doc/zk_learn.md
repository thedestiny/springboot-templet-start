分布式通知/协调
ZooKeeper 中特有 Watcher 注册与异步通知机制，能够很好的实现分布式环境下不同系统之间的通知与协调，实现对数据变更的实时处理。
使用方法通常是不同系统都对 ZooKeeper 上同一个 Znode 进行注册，监听 Znode 的变化（包括 Znode 本身内容及子节点的），
其中一个系统 Update 了 Znode，那么另一个系统能够收到通知，并作出相应处理。

另一种心跳检测机制：检测系统和被检测系统之间并不直接关联起来，而是通过 ZooKeeper 上某个节点关联，大大减少系统耦合。

另一种系统调度模式：某系统有控制台和推送系统两部分组成，控制台的职责是控制推送系统进行相应的推送工作。管理人员在控制台作的一些操作，
实际上是修改了 ZooKeeper 上某些节点的状态，而 ZooKeeper 就把这些变化通知给它们注册 Watcher 的客户端，即推送系统。
于是，作出相应的推送任务。

另一种工作汇报模式：一些类似于任务分发系统。子任务启动后，到 ZooKeeper 来注册一个临时节点，并且定时将自己的进度进行汇报
（将进度写回这个临时节点）。这样任务管理者就能够实时知道任务进度。

分布式锁
分布式锁主要得益于 ZooKeeper 为我们保证了数据的强一致性。锁服务可以分为两类：一类是保持独占，另一类是控制时序。

所谓保持独占，就是所有试图来获取这个锁的客户端，最终只有一个可以成功获得这把锁。通常的做法是把 ZooKeeper 上的一个 Znode 看作是
一把锁，通过 create znode的方式来实现。所有客户端都去创建 /distribute_lock 节点，最终成功创建的那个客户端也即拥有了这把锁。
控制时序，就是所有视图来获取这个锁的客户端，最终都是会被安排执行，只是有个全局时序了。做法和上面基本类似，只是这里 
/distribute_lock 已经预先存在，客户端在它下面创建临时有序节点（这个可以通过节点的属性控制：CreateMode.EPHEMERAL_SEQUENTIAL来指定）。
ZooKeeper 的父节点（/distribute_lock）维持一份 sequence，保证子节点创建的时序性，从而也形成了每个客户端的全局时序。

由于同一节点下子节点名称不能相同，所以只要在某个节点下创建 Znode，创建成功即表明加锁成功。注册监听器监听此 Znode，只要删除此 Znode 
就通知其他客户端来加锁。
创建临时顺序节点：在某个节点下创建节点，来一个请求则创建一个节点，由于是顺序的，所以序号最小的获得锁，当释放锁时，通知下一序号获得锁。

分布式队列
队列方面，简单来说有两种：一种是常规的先进先出队列，另一种是等队列的队员聚齐以后才按照顺序执行。对于第一种的队列和上面讲的分布式锁
服务中控制时序的场景基本原理一致，这里就不赘述了。
第二种队列其实是在 FIFO 队列的基础上作了一个增强。通常可以在 /queue 这个 Znode 下预先建立一个 /queue/num 节点，并且赋值为 n
（或者直接给 /queue 赋值 n）表示队列大小。之后每次有队列成员加入后，就判断下是否已经到达队列大小，决定是否可以开始执行了。
这种用法的典型场景是：分布式环境中，一个大任务 Task A，需要在很多子任务完成（或条件就绪）情况下才能进行。这个时候，凡是其中一个子
任务完成（就绪），那么就去 /taskList 下建立自己的临时时序节点（CreateMode.EPHEMERAL_SEQUENTIAL）。当 /taskList 发现自己下面的
子节点满足指定个数，就可以进行下一步按序进行处理了。

docker-compose.yml 创建 zk 集群，配置文件内容如下：
```
version: '3.4'

services:
  zoo1:
    image: zookeeper
    restart: always
    hostname: zoo1
    ports:
      - 2181:2181
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=0.0.0.0:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo2:
    image: zookeeper
    restart: always
    hostname: zoo2
    ports:
      - 2182:2181
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=0.0.0.0:2888:3888;2181 server.3=zoo3:2888:3888;2181

  zoo3:
    image: zookeeper
    restart: always
    hostname: zoo3
    ports:
      - 2183:2181
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zoo1:2888:3888;2181 server.2=zoo2:2888:3888;2181 server.3=0.0.0.0:2888:3888;2181

```

配置文件说明
```

# The number of milliseconds of each tick
# tickTime：CS通信心跳数
# Zookeeper 服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个 tickTime 时间就会发送一个心跳。tickTime以毫秒为单位。
tickTime=2000

# The number of ticks that the initial
# synchronization phase can take
# initLimit：LF初始通信时限
# 集群中的follower服务器(F)与leader服务器(L)之间初始连接时能容忍的最多心跳数（tickTime的数量）。
initLimit=5

# The number of ticks that can pass between
# sending a request and getting an acknowledgement
# syncLimit：LF同步通信时限
# 集群中的follower服务器与leader服务器之间请求和应答之间能容忍的最多心跳数（tickTime的数量）。
syncLimit=2

# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just
# example sakes.
# dataDir：数据文件目录
# Zookeeper保存数据的目录，默认情况下，Zookeeper将写数据的日志文件也保存在这个目录里。
dataDir=/data/soft/zookeeper-3.4.12/data

# dataLogDir：日志文件目录
# Zookeeper保存日志文件的目录。
dataLogDir=/data/soft/zookeeper-3.4.12/logs

# the port at which the clients will connect
# clientPort：客户端连接端口
# 客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1


# 服务器名称与地址：集群信息（服务器编号，服务器地址，LF通信端口，选举端口）
# 这个配置项的书写格式比较特殊，规则如下：
# server.N=YYY:A:B
# 其中N表示服务器编号，YYY表示服务器的IP地址，A为LF通信端口，表示该服务器与集群中的leader交换的信息的端口。
B为选举端口，表示选举新leader时服务器间相互通信的端口（当leader挂掉时，其余服务器会相互通信，选择出新的leader）。
一般来说，集群中每个服务器的A端口都是一样，每个服务器的B端口也是一样。但是当所采用的为伪集群时，IP地址都一样，只能时A端口和B端口不一样。

```

zk 基本操作
```
ls 查看当前包含的内容 ls /
create /zk myData 创建一个新的节点并关联
获取 znode 节点 zk。命令：get /zk
删除 znode 节点 zk。命令：delete /zk

```

zk 文章地址
https://mp.weixin.qq.com/s/I7KJeudEk1KX2LORybnNzg

zk 选举机制
https://mp.weixin.qq.com/s/ukY9aW3H0IvWE5bjJ9ROZg
zk 分布式事务
https://mp.weixin.qq.com/s/csw6s7HdNZv7-Mefk9wKzg
zk 分布式锁和队列1
https://mp.weixin.qq.com/s/WN0Q1hUicyCng5VCv83Rsg
zk 分布式锁和队列2
https://mp.weixin.qq.com/s/OLSmUp_d0VO3kaFkRUmiHg


###### zk 的 watcher 机制
客户端可以通过znode 上设置 watch 实现znode 的监听， watcher 是一个一次性触发器，当监听的节点数据发送变化时，通知客户端
zk 只通知有数据变化，不通知变化的内容

1 客户端线程
2 客户端watchManager 
3 zk的服务器，回调是串行同步的

崩溃恢复和广播模式
zk 是主从模式

网络分区时，是选择A还是C
zk是分布式协调服务

eureka 保证AP,节点是平等的






