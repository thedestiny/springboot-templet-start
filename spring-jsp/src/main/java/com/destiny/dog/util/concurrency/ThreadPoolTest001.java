package com.destiny.dog.util.concurrency;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class ThreadPoolTest001 {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		Executor executor = Executors.newFixedThreadPool(3);
		
		CountDownLatch latch = new CountDownLatch(34);
		Semaphore semaphore = new Semaphore(3);
		
		for (int i = 0; i < 10; i++) {
			
			semaphore.acquire(1);
			
			Runnable tmp = () -> {
				System.out.println("3333");
			};
			executor.execute(tmp);
			semaphore.release();
		}
		
		
	}
	
}
