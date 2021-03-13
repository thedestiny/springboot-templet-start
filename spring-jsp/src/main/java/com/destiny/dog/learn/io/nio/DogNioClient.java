package com.destiny.dog.learn.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class DogNioClient {
	
	private final InetSocketAddress serverAdrress = new InetSocketAddress("localhost", 8080);
	private Selector selector = null;
	private SocketChannel client = null;
	
	private String nickName = "";
	private Charset charset = Charset.forName("UTF-8");
	private static String USER_EXIST = "系统提示：该昵称已经存在，请换一个昵称";
	private static String USER_CONTENT_SPLIT = "#@#";
	
	
	public DogNioClient() throws IOException {
		
		// 不管三七二十一，先把路修好，把关卡开放
		// 连接远程主机的IP和端口
		client = SocketChannel.open(serverAdrress);
		client.configureBlocking(false);
		
		//开门接客
		selector = Selector.open();
		client.register(selector, SelectionKey.OP_READ);
	}
	
	public void session() {
		//开辟一个新线程从服务器端读数据
		new Reader().start();
		//开辟一个新线程往服务器端写数据
		new Writer().start();
	}
	
	private class Writer extends Thread {
		
		@Override
		public void run() {
			try {
				//在主线程中 从键盘读取数据输入到服务器端
				Scanner scan = new Scanner(System.in);
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					if ("".equals(line)) continue; //不允许发空消息
					if ("".equals(nickName)) {
						nickName = line;
						line = nickName + USER_CONTENT_SPLIT;
					} else {
						line = nickName + USER_CONTENT_SPLIT + line;
					}
//		            client.register(selector, SelectionKey.OP_WRITE);
					client.write(charset.encode(line));//client既能写也能读，这边是写
				}
				scan.close();
			} catch (Exception e) {
			
			}
		}
		
	}
	
	
	private class Reader extends Thread {
		public void run() {
			try {
				// 事件的获取和消息的处理 轮询
				while (true) {
					// 查询可以处理的socket
					int channels = selector.select();
					if (channels > 0) {
						// 内核空间 jvm空间 用户空间
						Set<SelectionKey> selectedKeys = selector.selectedKeys();  //可以通过这个方法，知道可用通道的集合
						Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
						while (keyIterator.hasNext()) {
							SelectionKey key = keyIterator.next();
							// 删除jvm 空间和用户空间的socket,否则会出现重复的情况，jvm 会在此基础之上做追加
							keyIterator.remove();
							// 处理事件
							process(key);
						}
					}
				}
			} catch (IOException io) {
				System.out.println("异常信息:" + io.getMessage());
			}
		}
		
		private void process(SelectionKey key) throws IOException {
			if (key.isReadable()) {
				//使用 NIO 读取 Channel中的数据，这个和全局变量client是一样的，因为只注册了一个SocketChannel
				//client既能写也能读，这边是读
				SocketChannel sc = (SocketChannel) key.channel();
				
				ByteBuffer buff = ByteBuffer.allocate(1024);
				StringBuilder content = new StringBuilder();
				while (sc.read(buff) > 0) {
					buff.flip();
					content.append(charset.decode(buff));
				}
				String cnt = content.toString();
				//若系统发送通知名字已经存在，则需要换个昵称
				if (USER_EXIST.equals(cnt)) {
					nickName = "";
				}
				System.out.println(cnt);
				key.interestOps(SelectionKey.OP_READ);
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		new DogNioClient().session();
	}
	
}
