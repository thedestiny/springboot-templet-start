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


noeviction:返回错误当内存限制达到并且客户端尝试执行会让更多内存被使用的命令（大部分的写入指令，但DEL和几个例外）
allkeys-lru: 尝试回收最少使用的键（LRU），使得新添加的数据有空间存放。
volatile-lru: 尝试回收最少使用的键（LRU），但仅限于在过期集合的键,使得新添加的数据有空间存放。
allkeys-random: 回收随机的键使得新添加的数据有空间存放。
volatile-random: 回收随机的键使得新添加的数据有空间存放，但仅限于在过期集合的键。
volatile-ttl: 回收在过期集合的键，并且优先回收存活时间（TTL）较短的键,使得新添加的数据有空间存放。

如果没有键满足回收的前提条件的话，策略volatile-lru, volatile-random以及volatile-ttl就和noeviction 差不多了。

