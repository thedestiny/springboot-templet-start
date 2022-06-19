

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


```