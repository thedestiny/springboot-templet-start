package com.destiny.dog.learn.io.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * client handler 心跳、超时检测、连接状态
 */
public class AppClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	
	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
		System.out.println("接收到的消息：" + byteBuf.toString(CharsetUtil.UTF_8));
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 处理I/O事件的异常
		cause.printStackTrace();
		ctx.close();
	}
	
	
}
