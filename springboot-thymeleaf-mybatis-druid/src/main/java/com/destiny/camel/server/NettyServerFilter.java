package com.destiny.camel.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author destiny
 * @Date 2021-06-16 7:49 PM
 */
@Slf4j
public class NettyServerFilter extends ChannelInitializer<SocketChannel> {



    @Override
    protected void initChannel(SocketChannel socketChannel) {

        ChannelPipeline ph = socketChannel.pipeline();
        ph.addLast("encoder",new HttpResponseEncoder());
        ph.addLast("decoder",new HttpRequestDecoder());
        ph.addLast("aggregator", new HttpObjectAggregator(10*1024*1024));//把单个http请求转为FullHttpReuest或FullHttpResponse
        ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
    }
}
