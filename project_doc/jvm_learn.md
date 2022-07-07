

```
https://www.bilibili.com/video/BV1DW411s7w


# java 编译 
https://www.bilibili.com/read/cv13799762
# java 编译
https://www.csdn.net/tags/Mtjakg4sMDA0MzctYmxvZwO0O0OO0O0O.html


Java代码要想放到JVM里去运行，首先需要经过Javac的编译，将Java代码编译为字节码Class文件。
Class文件反汇编后就是一条条JVM指令了，但是这些指令JVM认识，计算机可不认识。

解释执行
将JVM指令逐行翻译为本地机器码，逐行解释，逐行执行。 程序启动块，执行慢

编译执行
将Class文件直接编译成本地机器码并缓存下来，CPU可以直接执行。 启动慢，执行快

jvm 只是一种规范
HotSpot 虚拟机
-Xint  均是解释执行
-Xcomp 优先编译执行，无法执行的还需要编译器介入工作

jit 即时编译

热点探测 基于采样的热点探测和基于计数器的热点探测，hotspot 采用基于计数器的热点探测

hotspot 方法调用计数器 和回边计数器

方法调用计数器
client 1500 次 server 10000次调用阈值 
-XX:CompileThreshold=10000
-XX:-UseCounterDecay 次数减半控制

缓冲 对数据进行暂存，然后进行批量的传输或者读写操作，多采用顺序操作，来缓解不同设备之间缓慢的读写操作。

java -XX:+PrintCommandLineFlags -version

java -XX:+PrintFlagsFinal -XX:+UseG1GC  2>&1 | grep UseAdaptiveSizePolicy

虽然配置的最大的堆内存，但是只有在使用时才会真正的进行分配，
-XX:+AlwaysPreTouch

ES 需要预留一般的内存给文件缓存 


jmap -histo:live pid | head -n 10 查看前10的堆内对象的示例统计信息
String char[] 优化为了 byte[]
char 在 jvm 中占用两个字节，使用 utf-8 编码，则其范围为 \u0000（0）和 \uffff（65,535）

https://blog.csdn.net/YimBa/article/details/125025298

cms 产生的问题为 浮动垃圾 内存碎片 漏标的情况

G1
ZGC 
Shennandan
Esplion

三色标记算法 黑白灰 

SMP(Symmetric Multiprocessing)， 即对称多处理器架构，是目前最常见的多处理器计算机架构。
AMP(Asymmetric Multiprocessing)， 即非对称多处理器架构，则是与SMP相对的概念。
非统一内存访问（NUMA） 
均匀内存访问 UMA
https://blog.csdn.net/qq_34556414/article/details/123076197

多个CPU通过同一个北桥(North Bridge)芯片与内存链接。

https://blog.csdn.net/qq_20817327/article/details/105925071

https://blog.csdn.net/weixin_45101064/article/details/123478022

Epsilon主要是用于测试的无操作收集器，如：性能测试、内存压力测试、VM接口测试.期间不会发生GC,程序分配的内存耗尽就会因为 oom 而退出。
G1收集器是开启了整堆回收的里程碑
CMS收集器是JVM中开辟并发收集的里程碑，而本次的主角G1则是开创GC分区回收新时代的里程碑。
G1全称为Garbage-First Garbage Collector（垃圾优先收集器）
-XX:+UseG1GC装配它。G1是一款专门针对于拥有多核处理器和大内存的机器的收集器，

HotSpot的源码TARGET_REGION_NUMBER定义了Region区的数量限制为2048个
-XX:MaxGCPauseMills参数设定GC预期回收停顿时间值，那么G1默认为200ms。
局部复制，全局标记-理整

STAB + 写屏障 snapshot at the begining 

染色指针 
Remebered Set 双向卡表
连接矩阵

https://blog.csdn.net/weixin_45101064/category_11685806.html

-- 只拉去最近一次提交
git clone --depth 1 <repo_uri>


```

[](./images/jvm_gcs.png)