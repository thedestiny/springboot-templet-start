### springboot-templet-start

#### 1 项目简介

当下利用 `springboot` 开发项目已经是主流趋势，`springboot` 的优势是显而易见的，为了总结过往的项目经验，创建了一个模板项目，来整理自己所学的知识。同时也不能忘记 `spring`的辉煌过往，也整理了基于spring 创建项目的模板，现在主流的是前后端分离项目，但是也不能忘记之前的 `jsp、freemarker、thymeleaf、velocity` 。最后也会总结一下项目经验和所掌握的知识。目前规划的有：

```
1 springboot 整合项目模板工程
2 spring 整合项目模板工程
3 jvm 知识点总结和最佳实践
4 高并发知识点总结和最佳实践
5 mysql 知识点总结和最佳实践
6 mybatis 知识点总结和最佳实践
7 spring 知识点总结和最佳实践
8 elasticsearch 知识点总结和最佳实践
9 redis 知识点总结和最佳实践
10 mq 知识点总结和最佳实践
```

总结不易，且行且努力。

#### 2 项目说明

##### 2.1、spring-freemarker(cat)
技术架构 spring5.2.8RELEASE、freemarker、redis共享session，采用 tomcat 方式部署
mybatis plugins 字段脱敏 打印生成 sql 



##### 2.2、spring-jsp-mybatis-plus(dog)

技术架构 spring5.2.8RELEASE、jsp、mybatis-plus，采用 tomcat 方式部署

##### 2.3、spring-jsp(fox)
技术架构 spring5.2.8RELEASE、jsp、redis共享session，采用 tomcat 方式部署
util



##### 2.4、springboot-jsp-mybatis-druid-war(eagle)

技术架构 springboot2.3.3.RELEASE+lombok+jsp+mybatis+druid ，package 为war包，采用tomcat 方式部署

##### 2.5、springboot-jsp-mybatis-druid(horse)

技术架构 springboot2.3.3.RELEASE+lombok+jsp+mybatis+druid ，package 为jar包，采用 java -jar 方式部署

##### 2.6、springboot-thymeleaf-mybatis-druid(camel)

技术架构 springboot2.3.3.RELEASE+lombok+freemaker+mybatis+druid，package 为jar包，采用 java -jar 方式部署

##### 2.7、springboot-fremarker-mybatisplus-hikari(rabbit)

技术架构 springboot2.3.3.RELEASE+lombok+freemaker+mybatisplus+hikari，package 为jar包，采用 java -jar 方式部署

##### 2.8、springboot-thymeleaf(wolf)

技术架构 springboot2.3.3.RELEASE+lombok+thymeleaf, package 为jar包，采用 java -jar 方式部署

##### 2.9、springboot-dynamic-datasource(seal)
动态数据源实例

##### 2.10、springboot-nacos-dubbo-consumer(shrimp)


##### 2.11、springboot-nacos-dubbo-provider(lobster)

##### 2.12、springboot-freestyle(squirrel)


##### 2.13、springboot-destiny-starter(leopard)
豹子 starter 项目示例 
##### 2.14、springboot-dynamic-datasource(hiootamus)
河马 多数据源接入项目示例 

##### 2.15 springboot-sharding(cormorant)
鸬鹚 分库分表项目示例

redis 主从复制
https://www.cnblogs.com/kismetv/p/9236731.html

```

https://blog.csdn.net/weixin_67222106/article/details/125590431

SELECT TABLE_NAME as 'tab_name',TABLE_COMMENT as 'tab_com' FROM information_schema.TABLES WHERE table_schema = 'db_uat' and TABLE_NAME like "t_%" and TABLE_NAME not in ("t_trade_order", "t_trade_refund")


select COLUMN_NAME,COLUMN_COMMENT from information_schema.columns where  table_name='表名'

-- 查询数据库表字段
SELECT COLUMN_NAME as 'name',
DATA_TYPE as 'type' , COLUMN_COMMENT as 'comment' 
FROM information_schema.`COLUMNS`WHERE
 TABLE_SCHEMA = 'account' 
 AND TABLE_NAME= 'tb_user';
 


git commit  作者信息和邮箱修改
https://blog.csdn.net/u014641168/article/details/125414820

git filter-branch --env-filter '
oldEmail="old@163.com"
newName="kaiyang"
newEmail="kaiyang@qq.com"

if [ "$GIT_COMMITTER_EMAIL" = "$oldEmail" ]; then
    export GIT_COMMITTER_NAME="$newName"
    export GIT_COMMITTER_EMAIL="$newEmail"
fi

if [ "$GIT_AUTHOR_EMAIL" = "$oldEmail" ]; then
    export GIT_AUTHOR_NAME="$newName"
    export GIT_AUTHOR_EMAIL="$newEmail"
fi
' --tag-name-filter cat -- --branches --tags

```
