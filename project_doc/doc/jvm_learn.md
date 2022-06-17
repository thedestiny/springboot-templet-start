##### 垃圾回收器
https://www.cnblogs.com/yanl55555/p/13365572.html

垃圾回收器分为串行 并行 并发垃圾回收器
jdk8 默认的垃圾回收器为 ps+po

串行回收器：Serial. Serial Old 
并行回收器：ParNew. Parallel Scavenge. Parallel Old 
并发回收器：CMS. G1（分区算法）


新生代收集器： Serial、 ParNeW、Parallel Scavenge；
老年代收集器： Serial Old、 Parallel Old、 CMS；
整堆收集器： G1；

查看默认的垃圾回收器
-xx：+PrintCommandLineFlags

serial 串行垃圾回收器，采用复制算法
-XX:+UseSerialGC

parNew 并行垃圾回收器
-XX:+UseParNewGC

Parallel 吞吐量优先
-XX:+UseParallelGC 年轻代
-XX:+UseParallel0ldGc 老年代
-XX:ParallelGCThreads 指定垃圾回收器的线程数
-XX ：MaxGCPau3eMillis 垃圾回收器最大停顿时间
-XX：GCTimeRatio 垃圾收集器站总时间的比例
-XX： +UseAdaptiveSizePolicy 使用自适应的方式

CMS回收器:低延迟 标记清除算法
cms+serialO

-XX+UseConcMarkSweepGc 使用 cms
-XX:CMS1nitiatingOccupanyFraction=75 设置堆内存使用率的阈值超过75%时才开始进行回收，默认75%
-XX:+UseCMSInitiatingOccupancyOnly 只使用设定的值进行回收，如果不指定jvm第一次指定值，后续则自动调整
-XX:ParallelCMSThreads=3 设置 cms 垃圾回收器的线程数
-XX:+CMSScavengeBeforeRemark 在CMS GC回收前启动一次ygc，目的在于减少old gen对ygc gen的引用，降低remark时的开销-----一般CMS的GC耗时 80%都在remark阶段
-XX:CMSFullGCsBeforeCompaction设置在执行多少次Full GC后对内存空间进行压缩整理
-XX:+UseCMSCompactAtFullCollection用于指定在执行完Full GC后对内存空间进行压缩整理，
以此避免内存碎片的产生。不过由于内存压缩整理过程无法并发执行，所带来的问题就是停顿时间变得更长了。

G1 垃圾回收器
-XX：+UseG1GC 手动指定使用G1收集器执行内存回收任务。
-XX：G1HeapRegionSize 设置每个Region的大小。值是2的幂，范围是1MB 到32MB之间，
目标是根据最小的Java堆大小划分出约2048个区域。默认是堆内存的1/2000。
-XX：MaxGCPauseMillis 设置期望达到的最大Gc停顿时间指标（JVM会尽力实现，但不保证达到）。默认值是200ms
-xX：ParallelGCThread 设置sTw.工作线程数的值。最多设置为8
-XX：ConcGCThreads 设置并发标记的线程数。将n设置为并行垃圾回收线程数（ParallelGCThreads）的1/4左右。
-XX：Ini tiatingHeapOccupancyPercent 设置触发并发GC周期的Java堆占用率阈值。超过此值，就触发GC。默认值是45。


dump 内存信息当发生 oom情况 设置信息导出的路径
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=data
错误日志地址
-XX:ErrorFile=logs/hs_err_pid%p.log


8:-XX:+PrintGCDetails
8:-XX:+PrintGCDateStamps
8:-XX:+PrintTenuringDistribution
8:-XX:+PrintGCApplicationStoppedTime
8:-Xloggc:logs/gc.log
8:-XX:+UseGCLogFileRotation
8:-XX:NumberOfGCLogFiles=32
8:-XX:GCLogFileSize=64m

浮动垃圾





