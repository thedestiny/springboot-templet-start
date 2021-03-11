package com.destiny.dog.util.io.netty;

import com.destiny.dog.util.io.netty.handler.AppServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class DogNettyAppServer {
	
	private int port;
	
	public DogNettyAppServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		
		//Netty的Reactor线程池，初始化了一个NioEventLoop数组，用来处理I/O操作,如接受新的连接和读/写数据
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup work = new NioEventLoopGroup(8);
		
		try {
			
			ServerBootstrap b = new ServerBootstrap();//用于启动NIO服务
			b.group(boss, work)
					.channel(NioServerSocketChannel.class) //通过工厂方法设计模式实例化一个channel
					.localAddress(new InetSocketAddress(port))//设置监听端口
					.childHandler(new ChannelInitializer<SocketChannel>() {
						//ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel,用于把许多自定义的处理类增加到pipline上来
						@Override
						public void initChannel(SocketChannel ch) throws Exception {//ChannelInitializer 是一个特殊的处理类，他的目的是帮助使用者配置一个新的 Channel。
							ch.pipeline().addLast(new AppServerHandler());//配置childHandler来通知一个关于消息处理的InfoServerHandler实例
						}
					});
			
			//绑定服务器，该实例将提供有关IO操作的结果或状态的信息
			ChannelFuture channelFuture = b.bind().sync();
			System.out.println("在" + channelFuture.channel().localAddress() + "上开启监听");
			//阻塞操作，closeFuture()开启了一个channel的监听器（这期间channel在进行各项工作），直到链路断开
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("encounter exception and detail is {}", e.getMessage());
			
		} finally {
			boss.shutdownGracefully().sync();//关闭EventLoopGroup并释放所有资源，包括所有创建的线程
			work.shutdownGracefully().sync();//关闭EventLoopGroup并释放所有资源，包括所有创建的线程
		}
		
	}
	
	
	public static void main(String[] args) throws Exception {
		
		new DogNettyAppServer(18080).run();
	}
	
	
}
