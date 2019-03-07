#### templet-start项目启动模板

##### 1、templet-001

springboot2.1.3.RELEASE+lombok+thymeleaf

jar 包形式部署

##### 2、templet-002

springboot2.1.3.RELEASE+lombok+freemaker+mybatis+druid

jar 包形式部署

##### 3、templet-003

springboot2.1.3.RELEASE+lombok+jsp+mybatis+druid

jar 包形式部署

##### 4、templet-004

springboot2.1.3.RELEASE+lombok+jsp+mybatis+druid 

tomcat8.0+ war 包形式部署

```
mvn -U -e  clean package -Dmaven.test.skip=true
nohoup java -jar app.jar --spring.profiles.active=prod  > nohup.out & 后台运行
```







