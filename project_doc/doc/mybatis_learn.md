
1 mybatis 方法不支持重载
Mybatis 使用 package+Mapper+method全限名作为key，去xml内寻找唯一sql来执行的。类似：key=x.y.UserMapper.getUserById，那么，重载方法时将导致矛盾。对于Mapper接口，Mybatis禁止方法重载（overLoad）。

MapperProxy 中的 invoke 方法

SqlSessionFactoryBuilder
SqlSessionFactory
SqlSession sqlSession = factory.openSession()
sqlSession.getMapper(xxxMapper.class);
final MapperMethod mapperMethod = cachedMapperMethod(method);
return mapperMethod.execute(sqlSession, args);
