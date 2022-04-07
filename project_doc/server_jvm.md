cpu 架构
https://www.cnblogs.com/yubo/archive/2010/04/23/1718810.html
disruptor ringbuffer
https://juejin.cn/post/7077569924574478373

时间轮
https://blog.csdn.net/tianshan2010/article/details/106749830/

HashedWheelTimer本质上也是一个延迟队列
数据轮询定时任务

DelayQueue DelayQueue本质是PriorityQueue

ScheduledExecutorService 其本质也是类似DelayQueue

HashedWheelTimer时间轮是一个高性能，低消耗的数据结构，它适合用非准实时，延迟的短平快任务，例如心跳检测