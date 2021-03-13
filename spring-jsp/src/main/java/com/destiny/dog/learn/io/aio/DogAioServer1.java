package com.destiny.dog.learn.io.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class DogAioServer1 {
	
	private final static int default_port = 8082;
	
	private int port = 8080;
	
	public DogAioServer1(int port){
		this.port = port;
	}
	
	public void listen(){
		try{
			AsynchronousServerSocketChannel server = AsynchronousServerSocketChannel.open();
			server.bind(new InetSocketAddress(this.port));
			
			server.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
				
				@Override
				public void completed(AsynchronousSocketChannel client, Object attachment) {
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					client.read(buffer);
					buffer.flip();
					System.out.println(new String(buffer.array()));
				}
				
				@Override
				public void failed(Throwable exc, Object attachment) {
				
				}
				
			});
			
		}catch(Exception e){
		
		}
		
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public static void main(String[] args) {
		new DogAioServer1(default_port).listen();;
	}
}
