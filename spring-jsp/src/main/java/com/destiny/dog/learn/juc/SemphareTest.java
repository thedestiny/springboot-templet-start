package com.destiny.dog.learn.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemphareTest {
	
	public static void main(String[] args) {
		
		/**
		 * 是公平的方式
		 *
		 
		 availablePermits：获取剩余可用许可证。
		    drainPermits ：获取剩余可用许可证。
		 
		 * */
		Semaphore semaphore = new Semaphore(2);
		
		new Thread(()->{
			
			try {
				System.out.println("Thread1 start ");
				semaphore.acquire(2);
				TimeUnit.SECONDS.sleep(3);
				System.out.println("Thread1 end ");
				semaphore.release(2);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}).start();
		
		new Thread(()->{
			
			try {
				
				System.out.println("Thread2 start ");
				semaphore.acquire(3);
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Thread2 end ");
				semaphore.release(3);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}).start();
		
		new Thread(()->{
			
			try {
				System.out.println("Thread3 start ");
				semaphore.acquire(2);
				TimeUnit.SECONDS.sleep(2);
				System.out.println("Thread3 end ");
				semaphore.release(2);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}).start();
		
	}
}
