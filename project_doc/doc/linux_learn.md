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

###### 物理内存 交换区 swap buffer cache 

https://mp.weixin.qq.com/s/wuPJ1YbQuqQK7ZN4U5txBw

物理内存就是系统硬件提供的内存大小，是真正的内存，相对于物理内存，在linux下还有一个虚拟内存的概念，
虚拟内存就是为了满足物理内存的不足而提出的策略，它是利用磁盘空间虚拟出的一块逻辑内存，用作虚拟内存的磁盘空间被称为交换空间（Swap Space）。

linux 会在物理内存不足时，使用交换分区的虚拟内存存储信息
Linux的内存管理采取的是分页存取机制，为了保证物理内存能得到充分的利用，内核会在适当的时候将物理内存中不经常使用的数据块自动交换到虚拟内存中，而将经常使用的信息保留到物理内存。
linux内核根据”最近最经常使用“算法，仅仅将一些不经常使用的页面文件交换到虚拟 内存

大量数据需要从磁盘读取到内存或者由内存写入磁盘时，系统的读写性 能就变得非常低下，因为无论是从磁盘读数据，还是写数据到磁盘，
都是一个很消耗时间和资源的过程，在这种情况下，Linux引入了buffers和 cached机制。

buffers是用来缓冲块设备做的，它只记录文件系统的元数据（metadata）以及 tracking in-flight pages，而cached是用来给文件做缓冲。
更通俗一点说：buffers主要用来存放目录里面有什么内容，文件的属性以及权限等等。而cached直接用来记忆我们打开过的文件和程序。

一般系统是不会自动释放内存的关键的配置文件/proc/sys/vm/drop_caches。这个文件中记录了缓存释放的参数，默认值为0，
也就是不释放缓存。他的值可以为0~3之间的任意数字，代表着不同的含义：
0 – 不释放
1 – 释放页缓存
2 – 释放dentries和inodes
3 – 释放所有缓存


free -h  人类可读的形式展示内存使用情况

lscpu 

redis 为什么快?
1 纯内存操作，
2 异步处理IO

redis 执行命令的步骤
接收。通过TCP接收到命令，可能会历经多次TCP包、ack、IO操作
解析。将命令取出来
执行。到对应的地方将value读出来
返回。将value通过TCP返回给客户端，如果value较大，则IO负荷会更重

timer 事件和 io 事件


io 多路复用和事件驱动
select
poll
epoll

https://zhuanlan.zhihu.com/p/144805500


```

# 磁盘操作 查看资源占用情况
df -hla 
du -h -m --max-depth=1 ./  |  sort -nr
```
