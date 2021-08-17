package com.destiny.dog.learn.io.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-17 1:57 PM
 */
@Slf4j
public class AppClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new AppClientHandler());//添加我们自定义的Handler
    }
}
