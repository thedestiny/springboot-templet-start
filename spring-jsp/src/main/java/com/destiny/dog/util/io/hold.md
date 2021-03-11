https://www.jianshu.com/p/b36bf23c777b

bio 阻塞 
一个连接一个线程，客户端有连接请求时服务器端就需要启动一个线程进行处理,线程开销大。
nio 非阻塞
一个请求一个线程，客户端发送的连接请求会注册到多路复用器上，多路复用器轮询到该连接有I/O请求时才启动一个线程进行处理；
aio 非阻塞异步
一个有效请求一个线程，客户端的I/O请求都是由OS先完成了再通知服务器应用去启动线程进行处理。

1 bio 是面向流的，而 nio 是面向缓冲的。
2 BIO的各种流是阻塞的，而NIO是非阻塞的；
3 BIO的Stream是单向的，而NIO的channel是双向的

NIO的的显著特点：事件驱动模型、单线程处理多任务、非阻塞I/O，I/O读写不再阻塞，而是返回0、
基于block的传输比基于流的传输更高效、更高级的IO函数zero-copy、IO多路复用大大提高了Java网络应用的可伸缩性和实用性。
基于Reactor线程模型。

1）IO 线程模型：同步非阻塞，用最少的资源做更多的事；
2）内存零拷贝：尽量减少不必要的内存拷贝，实现了更高效率的传输；
3）内存池设计：申请的内存可以重用，主要指直接内存。内部实现是用一颗二叉查找树管理内存分配情况；
4）串形化处理读写：避免使用锁带来的性能开销；
5）高性能序列化协议：支持 protobuf 等高性能序列化协议。

dubbo 
rocketmq 
spark
stom
akka

大致了解一下Netty的主要组件及概念：

1）I/O：各种各样的流（文件、数组、缓冲、管道。。。）的处理（输入输出）；
2）Channel：通道，代表一个连接，每个Client请对会对应到具体的一个Channel；
3）ChannelPipeline：责任链，每个Channel都有且仅有一个ChannelPipeline与之对应，里面是各种各样的Handler；
4）handler：用于处理出入站消息及相应的事件，实现我们自己要的业务逻辑；
5）EventLoopGroup：I/O线程池，负责处理Channel对应的I/O事件；
6）ServerBootstrap：服务器端启动辅助对象；
7）Bootstrap：客户端启动辅助对象；
8）ChannelInitializer：Channel初始化器；
9）ChannelFuture：代表I/O操作的执行结果，通过事件机制，获取执行结果，通过添加监听器，执行我们想要的操作；
10）ByteBuf：字节序列，通过ByteBuf操作基础的字节数组和缓冲区。

