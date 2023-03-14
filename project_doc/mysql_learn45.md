##### 6 全局锁和表锁
数据库锁设计的初衷是处理并发问题。作为多用户共享的资
源,当出现并发访问的时候，数据库需要合理地控制资源的访问规则。锁就是用来实现这些规则的重要数据结构。

mysql 锁可以分为 全局锁 表锁 行锁
确保数据库处于只读状态，对数据库做备份，全局锁的目的就是为了全库逻辑备份
主库备份影响业务，从库备份binlog执行，主从延迟
ftwrl  flush tables with read lock,阻塞更新语句、数据库定义、事务提交

事务开启前会拿到一个一致性视图
single-transaction 适用于所有使用事务引擎的库
ftwrl 适用所有引擎的数据库

set global readonly=true 是全库只读，用来做主库或者从库标识
ftwrl 异常后会释放全局锁，儿readonly 需要手动修改，数据数据库配置

表锁和元数据锁

mdl 读锁 增删改查 
mdl 写锁 数据库结构变更

mrr multi range 

##### 7 mysql 行锁
mysql 行锁是在引擎层由各个引擎实现的，myisam 不支持行锁，只支持表锁
innodb 支持行锁

并发系统中不同线程出现循环资源依赖的情况，多个线程相互等待的情况就是死锁
innodb_lock_wait_timeout 设置超时时间默认是50s
innodb_deadlock_detect  on  开启死锁检测,默认是开启死锁检测

每个新来的线程都要检测自己的加入是否会导致死锁，时间复杂度为o(n),死锁检测需要消耗大量时间
消耗cpu资源，每秒的事务数不高
InnoDB的数据是按数据页为单位来读写的，每个数据页的大小是16KB

局部性原理 空间局部性和时间局部性

普通索引和唯一索引

唯一索引不能使用 change buffer 
innodb_change_buffer_max_size 50 change buffer 只能占到 buffer pool 的50%

将数据从磁盘读入内存涉及随机io的访问

普通索引和唯一索引

唯一索引需要检查是否冲突，普通索引是将更新记录在 change buffer 中

change buffer 适用于读少写多的场景，在写数据后执行读操作会触发merge操作

wal 



##### 8 mvcc 事务



##### 10 mysql 会选错索引



start transaction with consistent snapshot;
start transaction;

优化器是选择代价最小的方案去执行，扫描行数就是影响的因素之一 扫描行数 排序 临时表

mysql 中存储索引的统计方式 

innodb_stats_persistent 
on 持久化存储 N 20 M 10
off 内存存储 N 8 M 16


查询隐藏字段
select _rowid from t_user


XA是一个分布式事务协议
tcc 
事务消息+最终一致性

可以使用 force index 来强制使用某个索引


优化器逻辑
show index from table_name;
cardinality 基数, 是基于采样统计的方剂计算而来
扫描行数、是否使用临时表、是否排序

优化器选择索引的时候是根据最小代价去执行，时间成本和

MySQL 优化器计算的成本主要包括 I/O 成本和 CPU 成本

使用普通索引需要把回表的时间成本考虑进去，会导致mysql选错索引 

重新统计索引信息
analyze table t_test;

针对字段的前6个字节做索引
ALTER TABLE `victory_focus_list` ADD INDEX `test`(`name`(20)) USING BTREE;

前缀索引需要定义好长度，这样即节省空间又不会额外增加太多的查询成本
索引越长，占用的磁盘空间也就越大，相同的数据也能放下的索引值就越小，搜索效率也会降低

查询对应的区分度
select
count(distinct left(email,4) as L4,
count(distinct left(email,5) as L5,
count(distinct left(email,6) as L6,
count(distinct left(email,7) as L7,
from t_user;

show table status like 'victory_etf_rate'
show index from victory_etf_rate

innodb_flush_log_at_trx_commit 
sync_binlog

https://www.cnblogs.com/dubhlinn/p/10735015.html


set @@auto_increment_offset = 1;     -- 起始值
set @@auto_increment_increment = 2;  -- 步长


```

mysql binlog  配置

log-bin = mysql-bin
binlog_format = mixed
server-id = 1
expire_logs_days = 10


```

```$xslt

# 一致性协议
https://mp.weixin.qq.com/s/ZyW3GupsCPN4cEGPBMofuw
# 缓存池
https://www.jianshu.com/p/c6a154c83560


LRU List 管理已经读取的页

数据放置位置在5/8处,比例为37%
show variables like 'innodb_old_blocks_pct';
数据放置在冷区时需要经过一段时间才能放进热区，默认为1000ms
show variables like 'innodb_old_blocks_time';

commit point 
check point 


```

查询是否为共享表空间
show variables like 'innodb_file_per_table'; 


https://www.cnblogs.com/leizia/p/16063406.html


```mysql

# 创建数据库
DROP DATABASE If Exists `novel_plus`;
CREATE DATABASE If Not Exists  `novel_plus` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
use novel_plus;



# 创建用户并授权 mysql57
GRANT ALL ON *.* TO destiny@'%' IDENTIFIED BY "Myroot123!"; 

# 创建用户并授权 mysql58
create user destiny@'%' identified by 'Myroot123!'; 
grant all privileges on *.* to destiny@'%'; 

# 修改账户密码
ALTER USER root@'localhost' IDENTIFIED WITH MYSQL_NATIVE_PASSWORD BY 'Myroot123!';
ALTER USER destiny@'%' IDENTIFIED WITH mysql_native_password BY 'Myroot123!';

# flink cdc 
CREATE USER 'user'@'localhost' IDENTIFIED BY 'password';
GRANT SELECT, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'user' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;

```


```  
https://blog.csdn.net/weixin_35794878/article/details/125730036
https://blog.51cto.com/u_14006572/3025363

mysql 查询服务
https://mp.weixin.qq.com/s/vMGBYX9sPYHdvk8CJXhaVg

# maven 仓库地址
https://mvnrepository.com/search?q=mail


```




