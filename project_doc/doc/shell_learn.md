1 linux 软件安装

1.1 源码安装
通过 config 文件检查是否缺少依赖
make & make install 执行make 会默认在当前目录查询 makefile 
make工具就会通过makefile文件来实现。makefile文件是一种按照某种语法来编写且定义了各个文件的依赖关系



1.2 rpm 安装
安装软件包 rpm -ivh xxx.rpm
查询软件包 rpm -qf 
验证软件包 rpm -v xxx.rpm
更新软件包 rpm -uvh xxx.rpm
删除软件包 rpm -e xxx.rpm


1.3 yum 安装



1.4 二进制安装



ls -l /home 显示home目录目录下所有文件
 
date -d '+%Y-%m-%d' 打印系统时间
date -s 20201127    设置系统时间


uname -a 显示所有信息 -m 显示CPU类型 -n 显示操作系统主机名 -s 显示操作系统类型 -r 显示操作系统版本


df -ihT 输出磁盘信息


ifconfig enps33 ip netmask 255.255.255.0

```
#vi /etc/sysconfig/network-scripts/ifcfg-eth0

```

用户操作
```
添加用户
useradd -d /home/lanj -m lanj
更改密码
passwd lanj
切换用户
su lanj

```


```

#!/bin/bash #告诉系统使用什么解析器
echo "Hello xiaolan !" # echo进行输出

授权操作
chmod +x ./hello.sh ./hello.sh


shell 变量命名
命名首个字符不能是数字，只能使用英文字母、数字和下划线
不能使用标点符号
不能使用bash中关键字

只读变量
#!/bin/bash
James="小皇帝"
readonly James
James="登哥"
删除变量
#!/bin/bash
James="小皇帝"
unset James
echo $James #不会有任何输出

string="qwert"
echo $(#string)
# 提取子字符串
echo $(string:1:3)
#查找字符串
echo 


```

磁盘io  iostat
ssh -p 22 root@ip 
