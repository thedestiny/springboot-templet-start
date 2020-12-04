https://blog.csdn.net/zdplife/article/details/90106518

表空间
InnoDB 所有数据都存放在一个空间中，称之为表空间，表空间由段，区，页组成。
innodb_file_per_table 参数：
InnoDB 默认情况下所有表的数据都存放在共享表空间 ibdata1 中，该参数决定了是否每为张表内的数据单独设置一个表空间。
如果开启该功能，单独表空间中只存放数据、索引、插入缓存 Bitmap 页，其它数据包括回滚信息，插入缓存索引页，系统事务信息等还是存放在原来的共享表空间中。

段 区 页 行

段
表空间由各个段组成，主要分为索引段，数据段以及回滚段。其中数据段存放在 B+ 树的叶子节点，索引段存放在 B+ 树的非叶子节点。

区

区由连续的页组成：
每个区的大小是固定的：1MB
默认页的大小是 16KB，所以一个区中有 64 个连续页。
innodb_page_size 参数，在 1.2.x 版本以后可以设置对页的大小进行设置。
当创建一个表时，并不是直接使用连续的 64 个页存放数据，而是先用每个段开始时的 32 个碎片页来存放数据，等使用完这些碎片页才开始申请 64 个连续页，这样做的主要目的对于一些小表，刚开始可以申请较少的空间，节省磁盘容量的开销。

页

InnoDB 存储引擎中，常见的页的类型有：

数据和索引页
undo 页
系统页
事务数据页
插入缓存 Bitmap 页
插入缓存空闲列表页
未压缩的二进制大对象页
压缩的二进制大对象页

行

InnoDB 存储引擎中，数据是按照行进行存放的，最多可以存放 7992 条行记录

sql 查询按照自定义顺序进行排序,如果不在记录中则排序在最前面
select * from tab_name order by field(field_name, str1, str2, str3)

执行计划 extra 
Using where; Using temporary; Using filesort

mysql 基本架构图
缓存 缓存查询结果
sql 语句和结果以键值对方式存储，缓存频繁失效标更新会导致缓存失效 命中率比较低

连接器 管理连接验证权限
show processlist 查看现在的链接，wait_timeout 默认8个小时
show variables like 'wait_timeout'; 查询链接等待时间

分析器 词法分析、语法分析


优化器 执行计划索引选择
选择索引 多变关联决定关联顺序
rbo 基于规则优化 cbo 基于成本优化

执行器 操作引擎返回结果

存储引擎 存储数据，提供读写接口 

redo log 数据修改先更新redo log 并更新内存， innodb 引擎会在适合时机记录到磁盘
redo log 有固定大小，循环写数据
redo log  

undo log 是为了实现事务的原子性， undo log 实现 mvcc 
在执行任何数据之前，先需要进行数据备份，然后进行修改，如果执行错误需要 rollback 

bin log 是 server 层日志
1 redo log 是innodb 特有的，bin log 是所有引擎都可以用的
2 redo log 是物理日志，记录了数据页的修改 而bin log 是逻辑日志，记录语句的原始逻辑
3 redo log 是循环写的，空间会用完 而 bin log 是可以追加的，不会产生覆盖

bin log 会记录所有的逻辑操作，追加的方式写入，一般用于全量备份

redo log 两阶段提交
先写 redo log 再写 bin log 
先写 bin log 再写 redo log 


repeat('b',1024*1024*5) 重复函数 
select @@max_allowed_packet;

max_allowed_packet控制communication buffer最大尺寸，当发送的数据包大小超过该值就会报错，我们都知道，MySQL包括Server层和存储引擎，
它们之间遵循2PC协议，Server层主要处理用户的请求：连接请求—>SQL语法分析—>语义检查—>生成执行计划—>执行计划—>fetch data；
存储引擎层主要存储数据，提供数据读写接口。

show profiles;
show profile cpu,block io for query 1;  
mysql.slow_log记录的是执行超过long_query_time的所有SQL，如果遵循MySQL开发规范，slow query不会太多，
但是开启了log_queries_not_using_indexes=ON就会有好多full table scan的SQL被记录，这时slow_log表会很大，对于RDS来说，一般只保留一天的数据，
在频繁insert into slow_log的时候，做truncate table slow_log去清理slow_log会导致MDL，影响MySQL稳定性。
建议将log_output=FILE，开启slow_log， audit_log，这样就会将slow_log，audit_log写入文件，
通过Go API处理这些文件将数据写入分布式列式数据库clickhouse中做统计分析。

由于MySQL是单进程多线程模型，一个SQL语句无法利用多个cpu core去执行，这也就决定了MySQL比较适合OLTP（特点：大量用户访问、逻辑读，索引扫描，
返回少量数据，SQL简单）业务系统，同时要针对MySQL去制定一些建模规范和开发规范，尽量避免使用Text类型，它不但消耗大量的网络和IO带宽，
同时在该表上的DML操作都会变得很慢。另外建议将复杂的统计分析类的SQL，建议迁移到实时数仓OLAP中，例如目前使用比较多的clickhouse，
里云的ADB，AWS的Redshift都可以，
做到OLTP和OLAP类业务SQL分离，保证业务系统的稳定性。
