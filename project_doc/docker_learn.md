
##### docker 

##### 1 安装docker centos 遇到的问题
```
# docker centos yum 安装软件报错
Error: Failed to download metadata for repo 'appstream': Cannot prepare internal mirrorlist: No URLs in mirrorlist
上面的报错信息意思是，从仓库 ‘appstream’ 下载元数据失败：由于镜像列表中没有 URL，不能准备内部镜像列表。

解决办法
进入到 yum 的 repo 目录中
cd /etc/yum.repos.d/
修改 centos 内容

sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*

生成缓存 
yum makecache 
yum update -y
yum -y install vim

docker run -d --name centos7 --privileged=true centos:7 /usr/sbin/init

# 安装  service 
yum install -y initscripts
# 安装 netstat 
yum install -y net-tools

yum install -y nginx


service firewall status
service firewall stop
service firewall restart
service firewall start

systemctl status firewalld
systemctl start firewalld
systemctl stop firewalld  
systemctl disable firewalld
systemctl enable firewalld

systemctl stop iptables
systemctl status iptables
systemctl start iptables


```

##### 2 docker 启动命令

```

cat /proc/version 
cat /etc/issue
cat /etc/redhat-release


systemctl:test
docker run -itd --privileged --name centos -p 8300:80  centos /bin/bash

docker run -itd --privileged --name centos -p 8300:80  couchbase/centos7-systemd  /bin/bash

docker run -itd --privileged --name centos1 -p 8400:80  centos systemctl:test

docker run -itd --privileged --name centos1 -p 8400:80  centos systemctl:test

docker run -itd --privileged --name nginx -p 8300:80  nginx /bin/bash

docker stop containerid 
docker start containerid 

docker attach 进入容器再退出导致容器停止

推荐使用 exec 的方式
docker exec -it  containerid -
docker exec -it 243c32535da7 /bin/bash

docker run -itd --privileged -p -v centos /bin/bash




```
