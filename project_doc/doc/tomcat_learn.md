
```xml
 <!--  tomcat 配置关闭端口要设置不同,防止同时关闭 -->
<Server port="8005" shutdown="SHUTDOWN">
  <Listener className="org.apache.catalina.startup.VersionLoggerListener" />
  <!-- Security listener. Documentation at /docs/config/listeners.html
  <Listener className="org.apache.catalina.security.SecurityListener" />
  -->
</Server>

```

https://www.cnblogs.com/wfd360/p/11314697.html
https://blog.csdn.net/qq_37372007/article/details/81586751
https://www.cnblogs.com/wuchangsoft/p/9313889.html
https://blog.csdn.net/achudk/article/details/78925081

1 jenkins 触发式构建
2 jenkins 参数化构建
3 jenkins 定时构建

push over ssh 
Deploy to container Plugin

linux 上修改文件格式
vim file.txt 
按 :
输入 set ff = unix 或者 set fileformat= unix 

免密登录配置
```
ssh-keygen -t rsa
ssh-copy-id -i id_rsa.pub 192.168.0.xx
chmod 644 authorized_keys
vi /etc/ssh/sshd_config
PermitRootLogin no

```

