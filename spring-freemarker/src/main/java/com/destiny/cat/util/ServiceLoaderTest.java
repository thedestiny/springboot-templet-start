package com.destiny.cat.util;

import java.util.concurrent.locks.ReentrantLock;

public class ServiceLoaderTest {
	
	
	/**
	 ServiceLoader是Java提供的一套SPI（Service Provider Interface，常译：服务发现）框架，用于实现服务提供方与服务使用方解耦
	 使用步骤：
	 1 定义服务接口
	 2 实现服务接口
	 3 注册实现类到 META-INF/services
	 4 加载服务
     
     jdbc 的执行步骤：
	 1 执行数据库驱动类加载（非必须）：Class.forName("com.mysql.jdbc.driver")
	 2 连接数据库：DriverManager.getConnection(url, user, password)
	 3 创建SQL语句：Connection#.creatstatement();
	 4 执行SQL语句并处理结果集：Statement#executeQuery()
	 5 释放资源：ResultSet#close()、Statement#close()与Connection#close()
	 
	 https://www.jianshu.com/p/a18499b5df1c
	 * */
	
	public static void main(String[] args) {
		
		// waitStatus 等待状态
		// 1 CANCELLED
		/**
		 * 0 Node初始化后默认值
		 * 1 CANCELLED 超时或者中断，节点被取消
		 * -1 signal 表示节点的状态等待唤醒
		 * -2 condition 节点在等待队列中，节点线程等待唤醒
		 * -3 PROPAGATE 向后传播，仅限于头结点
		 * */
		
		
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		lock.unlock();
	
	}
	
}
