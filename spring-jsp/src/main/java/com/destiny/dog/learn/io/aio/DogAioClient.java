package com.destiny.dog.learn.io.aio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class DogAioClient {
	
	private final AsynchronousSocketChannel client;
	
	public DogAioClient() throws Exception {
		
		// Asynchronous
		// BIO   Socket
		// NIO   SocketChannel
		// AIO   AsynchronousSocketChannel
		// 需要先创建一个 client AsynchronousSocketChannel
		client = AsynchronousSocketChannel.open();
	}
	
	public void connect(String host, int port) throws Exception {
		
		// 建立连接
		client.connect(new InetSocketAddress(host, port), null, new CompletionHandler<Void, Void>() {
			
			//实现IO操作完成的方法
			@Override
			public void completed(Void result, Void attachment) {
				
				try {
					client.write(ByteBuffer.wrap(("这是服务端发送数据:" + System.currentTimeMillis()).getBytes())).get();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("已发送至服务器");
			}
			
			// 实现IO操作失败的方法
			@Override
			public void failed(Throwable exc, Void attachment) {
				System.out.println("服务端发送失败:" + exc.getMessage());
			}
		});
		
		//下面这一段代码是只读数据
		final ByteBuffer bb = ByteBuffer.allocate(1024);
		client.read(bb, null, new CompletionHandler<Integer, Object>() {
					
					//实现IO操作完成的方法
					@Override
					public void completed(Integer result, Object attachment) {
						System.out.println("IO操作完成" + result);
						System.out.println("获取反馈结果" + new String(bb.array()));
					}
					
					//实现IO操作失败的方法
					@Override
					public void failed(Throwable exc, Object attachment) {
						exc.printStackTrace();
					}
				}
		);
		
		
		try {
			Thread.sleep(Integer.MAX_VALUE);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		int count = 10;
		final CountDownLatch latch = new CountDownLatch(count);
		
		for (int i = 0; i < count; i++) {
			latch.countDown();
			Runnable runnable = () ->{
				try {
					latch.await();
					// 监听 localhost 8000 端口
					new DogAioClient().connect("localhost", 8000);
				} catch (Exception e) {
				
				}
			};
			new Thread(runnable).start();
		}
		
		Thread.sleep(1000 * 60 * 10);
	}
}
