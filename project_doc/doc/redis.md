https://mp.weixin.qq.com/s/PMGYoySBrOMVZvRZIyTwXg

基于内存实现
数据都存储在内存里，减少了一些不必要的 I/O 操作，操作速率很快。

高效的数据结构
底层多种数据结构支持不同的数据类型，支持 Redis 存储不同的数据；
不同数据结构的设计，使得数据存储时间复杂度降到最低。
   
合理的数据编码
根据字符串的长度及元素的个数适配不同的编码格式。

合适的线程模型
I/O 多路复用模型同时监听客户端连接；
单线程在执行过程中不需要进行上下文切换，减少了耗时。

redis 中文文档 
http://www.redis.cn/ 

LRU是Redis唯一支持的回收方法。
redis.conf 
maxmemory 100mb # 限制内存最大使用大小 0 则是没有限制 64 位为 0 32位为 3G
maxmemory-samples 5 # 回收检查的取样数量


###### Redis的回收策略
```
allkeys-lru：从数据集（server.db[i].dict）中挑选最近最少使用的键（LRU）淘汰
volatile-lru：从已设置过期时间的数据集（server.db[i].expires）中挑选最近最少使用的数据淘汰
volatile-random：从已设置过期时间的数据集（server.db[i].expires）中任意选择数据淘汰
allkeys-random：从数据集（server.db[i].dict）中任意选择数据淘汰
volatile-ttl：从已设置过期时间的数据集（server.db[i].expires）中挑选将要过期的数据淘汰
no-enviction（驱逐）：禁止删除数据,返回错误当内存限制达到并且客户端尝试执行会让更多内存被使用的命令（大部分的写入指令，但DEL和几个例外）

如果没有键满足回收的前提条件的话，策略volatile-lru, volatile-random以及volatile-ttl就和noeviction 差不多了。

Redis过期key的清理策略
（1）立即清理。在设置键的过期时间时，创建一个回调事件，当过期时间达到时，由时间处理器自动执行键的删除操作。
（2）惰性清理。键过期了就过期了，不管。当读/写一个已经过期的key时，会触发惰性删除策略，直接删除掉这个过期key
（3）定期清理。每隔一段时间，对expires字典进行检查，删除里面的过期键。默认每秒钟执行10次（HZ）
惰性删除加上定期删除



```


Redis 的瓶颈并不在 CPU，而在内存和网络
执行命令的核心模块还是单线程

Redis基于Reactor模式开发了网络事件处理器，这个处理器被称为文件事件处理器。它的组成结构为4部分：多个套接字、IO多路复用程序、文件事件分派器、事件处理器。
因为文件事件分派器队列的消费是单线程的，所以Redis才叫单线程模型

#### redis数据结构的编码方式
https://blog.csdn.net/snakorse/article/details/78154402

```
1 String 
int：8个字节的长整型。
embstr：小于等于39个字节的字符串。
raw：大于39个字节的字符串。
embstr 不可修改, 修改后变成raw格式

2 List
ziplist（压缩列表）：当哈希类型元素个数小于hash-max-ziplist-entries配置（默认512个）
同时所有值都小于hash-max-ziplist-value配置（默认64个字节）时，Redis会使用ziplist作为哈希的内部实现。
linkedlist（链表）：当列表类型无法满足ziplist的条件时，Redis会使用linkedlist作为列表的内部实现。
都是双端列表,quicklist节点每个值都是用ziplist进行存储
3.2 之前用 linkedList 3.2 之后用 quicklist


3 Hash
ziplist（压缩列表）：当哈希类型元素个数小于hash-max-ziplist-entries配置（默认512个），
同时所有值都小于hash-max-ziplist-value配置（默认64个字节）时，Redis会使用ziplist作为哈希的内部实现
ziplist使用更加紧凑的结构实现多个元素的连续存储，所以在节省内存方面比hashtable更加优秀。
hashtable（哈希表）：当哈希类型无法满足ziplist的条件时，Redis会使用hashtable作为哈希的内部实现。
因为此时ziplist的读写效率会下降，而hashtable的读写时间复杂度为O(1)。

4 Set 
intset（整数集合）：当集合中的元素都是整数且元素个数小于set-max-intset-entries配置（默认512个）时，
Redis会选用intset来作为集合内部实现，从而减少内存的使用。
hashtable（哈希表）：当集合类型无法满足intset的条件时，Redis会使用hashtable作为集合的内部实现。

5 Zset 

ziplist（压缩列表）：当有序集合的元素个数小于zset-max-ziplist-entries配置（默认128个）
同时每个元素的值小于zset-max-ziplist-value配置（默认64个字节）时，Redis会用ziplist来作为有序集合的内部实现，
ziplist可以有效减少内存使用。
skiplist（跳跃表）：当ziplist条件不满足时，有序集合会使用skiplist作为内部实现，因为此时zip的读写效率会下降。

```

缓存相关的问题
```
1 缓存穿透
2 缓存雪崩
3 缓存击穿
4 缓存一致性问题
  1 先删除缓存，再更新数据库，方案就是延时双删
  2 先更新数据库，再删除缓存，使用消息队列或者binlog同步
  3 设置数据过期时间
  
```


type keyName 查看key的类型
object encoding keyName 查看key 的编码方式


string int embstr raw 
list ziplist linkedlist 
hash ziplist hashtable 
set intset hashtable 
zset  ziplist skiplist 

定时删除 
定期删除
惰性删除
实际使用是用 惰性删除+定期删除

幂等分布需要使用 lru 算法
平均分布需要使用 random 算法

bg save 后台存储
aof 方式 或者 rdb 的方式

redis sentinel 着眼于高可用
redis cluster  着眼于扩展性

redis 支持的 java 客户端
redisson jedis lettuce 

config set requirepass 123456 


Redis 集群没有使用一致性 hash,而是引入了哈希槽的概念， Redis 集群有16384 个哈希槽，每个
key 通过 CRC16 校验后对 16384 取模来决定放置哪个槽， 集群的每个节点负责一部分 hash 槽


CRC16算法产生的hash值有16bit

16384
65535

redis 集群写数据并不是强一直性的，会有写丢失的情况，redis 之间复制数据是异步操作

集群无法选择数据库，只能在0号数据库 
ping 命令来测试节点之间的连通性
redis 序列 multi -> exec  discard watch 机制

2 ^ 32 

redis 的适用场景 
缓存会话 页面缓存  队列 排行榜和计数器 发布/订阅

适用 scan 方式获取数据
list  p2p队列
redis 延时队列 zset 
zrangebyscore 

redis 分布式锁 expire 


Redis 的IO多线程只是用来处理网络数据的读写和协议解析，执行命令仍然是单线程

```

#开启IO多线程
io-threads-do-reads yes

4核以上机器才建议开启多线程 
#配置线程数量，如果设为1就是主线程模式。
io-threads 4

```
