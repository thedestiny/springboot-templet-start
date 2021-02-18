
1.数据库设计和表创建时就要考虑性能

设计表时需要注意
1 表的字段避免 null 值出现，null 值不能进行查询优化，而且占用额外的索引空间
2 尽量使用 INT 而非 BIGINT，如果非负则加上UNSIGNED（这样数值容量会扩大一倍），当然能使用TINYINT、SMALLINT、MEDIUM_INT更好。
3 尽量使用TIMESTAMP而非DATETIME
4 单表不要有太多字段，建议在20以内
5 用整型来存IP

索引设计
1 索引不是越多越好，要根据查询条件的 on 、 group by 、where 和 order by 进行建立索引
2 尽量避免 where 子句对字段进行NULL值判断，否则会放弃使用索引而进行全表扫描
3 值分布的比较少的不适合建立索引
4 不要建立外键、不使用 unique 使用程序保证约束
5 整型比字符型的开销小，

使用 limit 对查询条件进行限制
按照需要返回需要查询的字段
使用连接 join 来代替子查询
拆分大量的 update 和 insert delete 
开启慢日志来分析慢日志
or 改写成 in 
WHERE子句中使用!=或<>操作符
连续范围 使用 between 不适用 in 

MyISAM适合SELECT密集型的表，而InnoDB适合INSERT和UPDATE密集型的表

























