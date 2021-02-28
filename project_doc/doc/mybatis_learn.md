
1 mybatis 方法不支持重载
Mybatis 使用 package+Mapper+method全限名作为key，去xml内寻找唯一sql来执行的。类似：key=x.y.UserMapper.getUserById，那么，
重载方法时将导致矛盾。对于Mapper接口，Mybatis禁止方法重载（overLoad）。

MapperProxy 中的 invoke 方法

SqlSessionFactoryBuilder
SqlSessionFactory
SqlSession sqlSession = factory.openSession()
sqlSession.getMapper(xxxMapper.class);
final MapperMethod mapperMethod = cachedMapperMethod(method);
return mapperMethod.execute(sqlSession, args);

在计算机的世界中，CPU的处理速度可谓是一马当先，远远甩开了其他操作，尤其是I/O操作，除了那种CPU密集型的系统，其余大部分的业务系统性能
瓶颈最后或多或少都会出现在I/O操作上，所以为了减少磁盘的I/O次数，那么缓存是必不可少的，通过缓存的使用我们可以大大减少I/O操作次数，
从而在一定程度上弥补了I/O操作和CPU处理速度之间的鸿沟。而在我们ORM框架中引入缓存的目的就是为了减少读取数据库的次数，从而提升查询的效率。

duplicate  重复的
perpetual  永久的
decorator  油漆匠 裱糊匠
Mybatis 的缓存相关都在 cache 下，默认只有一个实现类PerpetualCache，PerpetualCache中是内部维护了一个HashMap来实现缓存

mybatis 的一级缓存是 sqlsession 级别的， 二级缓存是 namespace 级别的
sqlSession  只是对外提供的接口，实际执行时 sql 的是 Executor 
BaseExecutor 中 PerpetualCache localCache 本地一级缓存
createCacheKey mappedStatement parameter rowBounds boundSql

清除一级缓存
flushCache = true 
完成一次查询后就清空缓存
commit rollback update 方法执行时会清空换存

装饰器模式 CachingExecutor 来实现二级缓存

cacheEnabled 默认值为 true
mapper 映射文件中加入 cache 标签
缓存集对象需要实现序列化
需要commit 之后缓存才能生效

TransactionalCacheManager 肯定就是用来管理二级缓存的
需要注意的是在事务提交之前，并不会真正存储到二级缓存，而是先存储到一个临时属性，等事务提交之后才会真正存储到二级缓存。

mybatis 中的缓存策略有:
readOnly 只读，设置为True所有相同的Sql语句返回的是同一个对象，有助于提高性能，在并发操作同一条SQL时，可能不安全。
设置为False，则相同的SQL后边访问的是Cache的副本。
size 指的是缓存多少的对象，默认为1024
flushInterval 指定缓存过期的时间，单位为毫秒，默认为空，只要缓存足够，永远不过期
eviction 指的是缓存的策略淘汰算法，默认淘汰算法是最近最少使用算法。还有先进先出算法。

```xml
<cache readOnly="true" size="1024" flushInterval="120000" eviction="LRU"/>  
```

常见的缓存策略
LRU 最近最少使用，移除最长时间不被使用的对象，为默认策略
FIFO 先进先出，按对象进入缓存的顺序来移除它们
SOFT 软引用，移除基于垃圾回收器状态和软引用规则的对象
WEAK 弱引用，更积极的移除基于垃圾收集器状态和弱引用规则对象


Interceptor
Executor         处理方法
ParameterHandler 处理参数
ResultSetHandler 处理返回结果集
StatementHandler 构建sql






