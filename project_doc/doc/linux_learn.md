##### 70 sed 
sed（Stream EDitor）是一种流文件编辑器，它一次处理一行内容。
处理时，把当前处理的行存储在临时缓冲区中，称为“模式空间”（Pattern Space），接着用 sed 命令处理缓冲区中的内容，处理完成后，把缓冲区的内容送往屏幕，接着处理下一行，直到文件末尾。
文件内容并没有改变，除非使用-i选项。sed 主要用来编辑一个或多个文件，简化对文件的反复操作或者用来编写转换程序等。
sed 功能同 awk 类似，差别在于，sed 简单，对列处理的功能要差一些，awk 功能复杂，对列处理的功能比较强大。


```
删除 2-5 行
nl -n ln /etc/passwd | sed '2,5d'
删除 3 到最后一行
nl /etc/passwd | sed '3,$d'
新增行信息
l -n ln /etc/passwd | sed '2a I like drinking tea'

第2行之前加入
nl /etc/passwd | sed '2i drink tea'
//或
nl /etc/passwd | sed '1a drink tea'
第二行后加入两行
nl -n ln /etc/passwd | sed '2a I like drinking tea\nI like drinking beer'

```




##### 71 uniq 
用于去除有序文件中的重复行并将结果输出到标准输出。
为了使uniq 起作用，所有的重复行必须是相邻的，所以 uniq 经常和 sort 合用。
```

uniq testfile  
文件内容
hello
world
friend
hello
world
hello

输出结果 cat testfile | sort | uniq

重复的次数 sort testfile | uniq -c 
仅显示重复的行 sort testfile | uniq -dc
仅显示不重复的行 sort testfile | uniq -u

```



##### 72 uptime 命令
uptime 用于显示系统总共运行了多长时间和系统的平均负载。
当前时间、系统已经运行了多长时间、目前有多少用户登录、系统在过去的 1 分钟、5 分钟和 15 分钟内的平均负载。输出结果等同于 top 命令汇总区的第一行。

```
uptime -V 显示版本号
uptime -s 显示系统的启动时间
uptime -p 输出系统的运行时长

系统的负载均衡是指单位时间内，系统中处于可运行状态和不可中断状态的进程数，也就是平均活跃进程数，他和 CPU 使用率没有直接关系。
1 可运行状态的进程指正在使用 CPU 或正在等待使用 CPU 的进程，也就是我们常用 ps 命令看到的，处于 R 状态（Running 或 Runnable）的进程。
2 不可中断状态的进程指正在等待某些 I/O 的进程，即我们在 ps 命令中看到的 D 状态（Uninterruptible Sleep，也称为 Disk Sleep）的进程。
例如等待磁盘 I/O，当一个进程向磁盘读写数据时，为了保证数据的一致性，在得到磁盘回复前，它是不能被其他进程打断的，这个时候的进程就处于
不可中断状态。如果此时的进程被打断了，就容易出现磁盘数据与进程数据不一致的问题。所以，不可中断状态实际上是系统对进程和硬件设备的一种保护机制。

读取文件 /proc/loadavg 可直接查看系统平均负载。
0.04 0.03 0.05 1/319 21900
除了前 3 个数字表示系统平均负载外，后面的一个分数，分母表示系统进程总数，分子表示正在运行的进程数；最后一个数字表示最近运行的进程 ID。

lscpu 查下看系统信息

grep 'model name'  /proc/cpuinfo | wc -l

```

平均负载和cpu使用率
```
在日常使用中，我们经常容易把平均负载和CPU使用率混淆，这里我们做下区分。
平均负载是指单位时间内，系统中处于可运行状态和不可中断状态的进程数，所以，他不仅包扩了正在使用CPU 的进程，还包括等待 CPU 和等待 I/O 的进程。
而 CPU 使用率，是单位时间内 CPU 繁忙情况的统计，和平均负载并不一定完全对应。比如：
（1）CPU 密集型进程，使用大量 CPU 会导致平均负载升高，此时这两者是一致的；
（2）I/O 密集型进程， 等待 I/O 也会导致平均负载升高，但是 CPU 使用率不一定很高；
（3）存在大量等待 CPU 调用的进程也会导致平均负载升高，此时的 CPU 使用率也会比较高。

```
