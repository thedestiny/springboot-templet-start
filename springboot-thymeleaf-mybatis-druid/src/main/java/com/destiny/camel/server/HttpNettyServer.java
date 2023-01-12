package com.destiny.camel.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @Description
 * @Author destiny
 * @Date 2021-06-16 7:45 PM
 * ContextLoaderListener
 * SpringBootContextLoaderListener
 * ApplicationListener
 * ServletContextListener
 * ServletContextListener
 * ApplicationListener<ContextRefreshedEvent>
 */
@Slf4j
@Component
public class HttpNettyServer implements ServletContextListener {

    private static final int port = 6789; //设置服务端端口
    private static EventLoopGroup group = new NioEventLoopGroup();   // 通过nio方式来接收连接和处理连接
    private static ServerBootstrap b = new ServerBootstrap();

    /**
     * Netty创建全部都是实现自AbstractBootstrap。
     * 客户端的是Bootstrap，服务端的则是    ServerBootstrap。
     **/
    public static void main(String[] args) throws InterruptedException {

    }

    public static void startServer() throws InterruptedException{
        try {
            b.group(group);
            b.channel(NioServerSocketChannel.class);
            b.childHandler(new NettyServerFilter()); //设置过滤器
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            log.info("服务端启动成功...");
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();
        } finally {
           //  group.shutdownGracefully();
        }
    }

    public static void stopServer(){

        if (group != null) {
            log.info("服务端关停成功...");
            // 关闭EventLoopGroup，释放掉所有资源包括创建的线程
            group.shutdownGracefully();
        }
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {
        log.info("start --- ");
        try {
            startServer();
        } catch (InterruptedException e) {
            log.error("服务启动失败!");
        }
    }


    @Override
    public void contextDestroyed(ServletContextEvent event) {

        stopServer();
    }

}
