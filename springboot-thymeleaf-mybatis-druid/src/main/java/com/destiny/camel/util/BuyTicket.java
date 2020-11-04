package com.destiny.camel.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.locks.ReentrantLock;

public class BuyTicket implements Runnable {
	
	// 定义去北京火车票
	private static Integer tickets = 10;
	
	// private CamelLock lock = new CamelLock();
	private static ReentrantLock lock = new ReentrantLock();
	
	@Override
	public void run() {
		while (true) {
			
			try {
				lock.lock(); //加锁
				Thread.sleep(10);
				if (tickets > 0) {
					System.out.println(Thread.currentThread().getName() + "-------->>>>>>正在出售第" + tickets-- + "张票");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();//释放锁
			}
		}
	}
	
	
	public static void main(String[] args) {
	
	
	
	
	
	
	
	}
	
}
