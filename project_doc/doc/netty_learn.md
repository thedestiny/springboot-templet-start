##### netty 
并发高 传输快 封装好 

BIO 等待客户端发数据的过程是阻塞的，一个线程只能处理一个请求
socket 建立好之后将其交给 selector,后者遍历请求所有的socket,一旦

https://www.jianshu.com/p/b9f3f6a16911
页缓存和socket缓冲区

nc baidu.com 80 

netstat -natp 

唯一的连接 socket 连接就是 ip:port+目标ip:目标port
一个socket 最多只能建立 65535 个链接


ip的七层协议 4层协议的作用
nio 和 bio 的区别是什么


###### NIO 
socket 可以支持阻塞和非阻塞
accept 属于系统调用 x80 软中断 空循环问题，需要减少系统调用
多路复用和事件驱动
select
poll
epoll

系统不需要 receive 结果，则是 aio
同步io 模型

channel 可读可写 buffer position capacity limit



1、NIO存在的问题：

NIO的API比较复杂，需要熟练掌握3个核心组件，channel、buffer和selector；
需要熟悉多线程、网络编程等技术；
开发工作量大，难度也比较大，需要解决断连、重连、网络闪断、半包读写、失败缓存、网络拥堵等各种情况；
NIO存在bug，一个叫Epoll的bug，会导致选择器空轮询，形成死循环，最后CPU飙到100%
正是因为NIO存在这些问题，netty就应运而生了。


netty是一个异步的，基于事件驱动的网络应用框架。可以快速地开发高性能的服务器端和客户端，像dubbo和elasticsearch底层都用了netty。
它具有以下优点：

    设计优雅，灵活可扩展；
    使用方便，用户指南清晰明确；
    安全，完整的SSL/TLS和StartTLS支持；
    社区活跃，不断地更新完善
1、线程模型：
目前存在的线程模式：传统阻塞IO的服务模型和Reactor模式

根据Reactor的数量和处理资源的线程数不同，又分3种：
1 单Reactor单线程；
2 单Reactor多线程；
3 主从Reactor多线程

Netty的线程模型是基于主从Reactor多线程做了改进。

2、传统阻塞IO的线程模型：
采用阻塞IO获取输入的数据，每个连接都需要独立的线程来处理逻辑。存在的问题就是，当并发数很大时，就需要创建很多的线程，占用大量的资源。连接创建后，如果当前线程没有数据可读，该线程将会阻塞在读数据的方法上，造成线程资源浪费。

3、Reactor模式(分发者模式/反应器模式/通知者模式)：
针对传统阻塞IO的模型，做了以下两点改进：

    基于IO复用模型：多个客户端共用一个阻塞对象，而不是每个客户端都对应一个阻塞对象
    基于线程池复用线程资源：使用了线程池，而不是每来一个客户端就创建一个线程

Reactor模式的核心组成：

    Reactor：Reactor就是多个客户端共用的那一个阻塞对象，它单独起一个线程运行，负责监听和分发事件，将请求分发给适当的处理程序来进行处理
    Handler：处理程序要完成的实际事件，也就是真正执行业务逻辑的程序，它是非阻塞的

I/O多路复用就是通过一种机制，一个进程可以监视多个描述符，一旦某个描述符就绪（一般是读就绪或者写就绪），
能够通知程序进行相应的读写操作。因为他们都需要在读写事件就绪后自己负责进行读写，也就是说这个读写过程是阻塞的，
而异步I/O则无需自己负责进行读写，异步I/O的实现会负责把数据从内核拷贝到用户空间。
异步io是写 buffer,内核通知应用程序处理完毕，可以进行操作
同步异步IO区别在于用户程序是否需要自己copy数据

select 通过 fd_set 传递文件描述符进行查询状态 read accept
select最大的缺陷就是单个进程所打开的FD是有一定限制的，它由FD_SETSIZE设置，默认值是1024,64位是2048
线性扫描结果，效率比较低
poll 通过 poll_fd 传递文件描述符,
没有最大连接数，文件没有状态变更时则挂起，直到就绪或者超时才进行再次遍历。大量的fd数组在内核和用户空间进行复制，比较浪费
epoll 没有最大连接数限制，不是采用轮询方式，而是采用回调的方式通知。内核开辟一块空间存储文件描述符，减少了复制操作，使用mmap 方式进行内存 copy


epoll_create 创建内核空间
epoll_ctl 
epoll_wait



jsp -l 
cd /proc/进程号/许多线程号

strace -ff out 


网络IO 
![](./images/123344455555321.png) 

netty option 和 childoption 参数配置
netty 创建服务端和客户端区别和联系
ServerBootstrap 

group

| 创建方式     | server                  | client                          |
| ------------ | ----------------------- | ------------------------------- |
| boot         | ServerBootstrap         | Bootstrap                       |
| group        | group(boss,worker)      | group                           |
| channel      | NioServerSocketChannel  | ServerSocketChannel             |
| localAddress | InetSocketAddress(port) | InetSocketAddress(address,port) |
| option       | childOption             | option                          |
| Handler      | childHandler            | handler                         |



option 参数区别

| 参数名                         | 备注                                                         | 应用                |
| :----------------------------- | ------------------------------------------------------------ | ------------------- |
| CONNECT_TIMEOUT_MILLIS         | 连接超时毫秒数，默认值30000毫秒即30秒。                      | 通用                |
| MAX_MESSAGES_PER_READ          | 一次Loop读取的最大消息数，对于ServerChannel或者NioByteChannel，默认值为16，其他Channel默认值为1。原因:ServerChannel需要接受足够多的连接，保证大吞吐量，NioByteChannel可以减少不必要的系统调用select。 | 通用                |
| WRITE_SPIN_COUNT               | 一个Loop写操作执行的最大次数，默认值为16。也就是说，对于大数据量的写操作至多进行16次，如果16次仍没有全部写完数据，此时会提交一个新的写任务给EventLoop，任务将在下次调度继续执行。这样，其他的写请求才能被响应不会因为单个大数据量写请求而耽误。 | 通用                |
| ALLOCATOR                      | ByteBuf的分配器，默认值ByteBufAllocator.DEFAULT，4.0版本为UnpooledByteBufAllocator，4.1版本为PooledByteBufAllocator。当然可以使用系统参数io.netty.allocator.type配置，使用字符串值："unpooled"，"pooled"。 | 通用                |
| RCVBUF_ALLOCATOR               | 用于Channel分配接受Buffer的分配器，默认值为AdaptiveRecvByteBufAllocator.DEFAULT，是一个自适应的接受缓冲区分配器，能根据接受到的数据自动调节大小。可选值为FixedRecvByteBufAllocator，固定大小的接受缓冲区分配器。 | 通用                |
| AUTO_READ                      | 自动读取，默认为True。Netty只在必要的时候才设置关心相应的I/O事件。对于读操作，需要调用channel.read()设置关心的I/O事件为OP_READ，这样若有数据到达才能读取以供用户处理。值为True，每次读操作完后会自动调用channel.read()，从而有数据到达便读取；否则，需要用户手动调用channel.read()。需要注意的是：当调用config.setAutoRead(boolean)方法时，如果状态由false变为true，将会调用channel.read()方法读取数据；由true变为false，将调用config.autoReadCleared()方法终止数据读取。 | 通用                |
| WRITE_BUFFER_HIGH_WATER_MARK   | 写高水位标记，默认值64KB。Netty的写缓冲区中的字节超过该值，Channel的isWritable()返回False。 | 通用                |
| WRITE_BUFFER_LOW_WATER_MARK    | 写低水位标记，默认值32KB。Netty的写缓冲区中的字节超过高水位之后若下降到低水位，则Channel的isWritable()返回True。写高低水位标记使用户可以控制写入数据速度，从而实现流量控制。推荐做法是：每次调用channl.write(msg)方法首先调用channel.isWritable()判断是否可写。 | 通用                |
| MESSAGE_SIZE_ESTIMATOR         | 消息大小估算器，默认为DefaultMessageSizeEstimator.DEFAULT。估算ByteBuf、ByteBufHolder和FileRegion的大小，其中ByteBuf和ByteBufHolder为实际大小，FileRegion估算值为0。该值估算的字节数在计算水位时使用，FileRegion为0可知FileRegion不影响高低水位。 | 通用                |
| SINGLE_EVENTEXECUTOR_PER_GROUP | 单线程执行ChannelPipeline中的事件，默认值为True。该值控制执行ChannelPipeline中执行ChannelHandler的线程。如果为Trye，整个pipeline由一个线程执行，这样不需要进行线程切换以及线程同步，是Netty4的推荐做法；如果为False，ChannelHandler中的处理过程会由Group中的不同线程执行。 | 通用                |
| SO_RCVBUF                      | TCP数据接收缓冲区大小。该缓冲区即TCP接收滑动窗口，linux操作系统可使用命令：cat /proc/sys/net/ipv4/tcp_rmem查询其大小。一般情况下，该值可由用户在任意时刻设置，但当设置值超过64KB时，需要在连接到远端之前设置。 | SocketChannel       |
| SO_SNDBUF                      | TCP数据发送缓冲区大小。该缓冲区即TCP发送滑动窗口，linux操作系统可使用命令：cat /proc/sys/net/ipv4/tcp_smem查询其大小。 | SocketChannel       |
| TCP_NODELAY                    | TCP参数，立即发送数据，默认值为Ture（Netty默认为True而操作系统默认为False）。该值设置Nagle算法的启用，改算法将小的碎片数据连接成更大的报文来最小化所发送的报文的数量，如果需要发送一些较小的报文，则需要禁用该算法。Netty默认禁用该算法，从而最小化报文传输延时。 | SocketChannel       |
| SO_KEEPALIVE                   | Socket参数，连接保活，默认值为False。启用该功能时，TCP会主动探测空闲连接的有效性。可以将此功能视为TCP的心跳机制，需要注意的是：默认的心跳间隔是7200s即2小时。Netty默认关闭该功能。 | SocketChannel       |
| SO_REUSEADDR                   | Socket参数，地址复用，默认值False。有四种情况可以使用：(1).当有一个有相同本地地址和端口的socket1处于TIME_WAIT状态时，而你希望启动的程序的socket2要占用该地址和端口，比如重启服务且保持先前端口。(2).有多块网卡或用IP Alias技术的机器在同一端口启动多个进程，但每个进程绑定的本地IP地址不能相同。(3).单个进程绑定相同的端口到多个socket上，但每个socket绑定的ip地址不同。(4).完全相同的地址和端口的重复绑定。但这只用于UDP的多播，不用于TCP。 | SocketChannel       |
| SO_LINGER                      | Socket参数，关闭Socket的延迟时间，默认值为-1，表示禁用该功能。-1表示socket.close()方法立即返回，但OS底层会将发送缓冲区全部发送到对端。0表示socket.close()方法立即返回，OS放弃发送缓冲区的数据直接向对端发送RST包，对端收到复位错误。非0整数值表示调用socket.close()方法的线程被阻塞直到延迟时间到或发送缓冲区中的数据发送完毕，若超时，则对端会收到复位错误。 | SocketChannel       |
| IP_TOS                         | IP参数，设置IP头部的Type-of-Service字段，用于描述IP包的优先级和QoS选项。 | SocketChannel       |
| ALLOW_HALF_CLOSURE             | Netty参数，一个连接的远端关闭时本地端是否关闭，默认值为False。值为False时，连接自动关闭；为True时，触发ChannelInboundHandler的userEventTriggered()方法，事件为ChannelInputShutdownEvent。 | SocketChannel       |
| SO_RCVBUF                      | 需要注意的是：当设置值超过64KB时，需要在绑定到本地端口前设置。该值设置的是由ServerSocketChannel使用accept接受的SocketChannel的接收缓冲区。 | ServerSocketChannel |
| SO_REUSEADDR                   | 重用地址                                                     | ServerSocketChannel |
| SO_BACKLOG                     | Socket参数，服务端接受连接的队列长度，如果队列已满，客户端连接将被拒绝。默认值，Windows为200，其他为128。 | ServerSocketChannel |

