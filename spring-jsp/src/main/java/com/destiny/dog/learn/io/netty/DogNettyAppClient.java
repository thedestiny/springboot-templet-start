package com.destiny.dog.learn.io.netty;

import com.destiny.dog.learn.io.netty.handler.AppClientChannelInitializer;
import com.destiny.dog.learn.io.netty.handler.AppClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class DogNettyAppClient {

    // 主机和端口号
    private final String host;
    private final int port;


    public DogNettyAppClient(String host, int port) {
        this.host = host;
        this.port = port;
    }


    public void run() throws Exception {

        //  配置相应的参数，提供连接到远端的方法
        //  I/O线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //客户端辅助启动类
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    //实例化一个Channel
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    //通道初始化配置
                    .handler(new AppClientChannelInitializer());

            //连接到远程节点；等待连接完成
            ChannelFuture future = bs.connect().sync();

            //发送消息到服务器端，编码格式是utf-8
            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8));

            //阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }

    }


    public static void main(String[] args) throws Exception {


        new DogNettyAppClient("127.0.0.1", 18080).run();

    }


}
