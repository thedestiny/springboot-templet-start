package com.destiny.dog.learn.io.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author destiny
 * @Date 2021-08-17 1:57 PM
 */
@Slf4j
public class AppServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //ChannelInitializer是一个特殊的处理类，他的目的是帮助使用者配置一个新的Channel,用于把许多自定义的处理类增加到pipline上来
        //ChannelInitializer 是一个特殊的处理类，他的目的是帮助使用者配置一个新的 Channel。
        ch.pipeline().addLast(new AppServerHandler());//配置childHandler来通知一个关于消息处理的InfoServerHandler实例

    }
}
