package com.destiny.dog.util.concurrency;


import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
public class ThreadPoolTest001 {
	
	
	public static void main(String[] args) throws InterruptedException {
		
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		InheritableThreadLocal<String> threadLocal1 = new InheritableThreadLocal<>();
		TransmittableThreadLocal<String> threadLocal2 = new TransmittableThreadLocal<String>();
		
		threadLocal.set("ThreadLocal -> 外部内容");
		threadLocal1.set("InheritableThreadLocal -> 外部内容");
		threadLocal2.set("TransmittableThreadLocal -> 外部内容");
		
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		CountDownLatch latch = new CountDownLatch(34);
		Semaphore semaphore = new Semaphore(3);
		
		for (int i = 0; i < 8; i++) {
			
			// semaphore.acquire(1);
			
			Runnable tmp = () -> {
				
				log.info("ThreadLocal {}", threadLocal.get());
				log.info("InheritableThreadLocal {}", threadLocal1.get());
				log.info("TransmittableThreadLocal  {}", threadLocal2.get());
				
				// String s = threadLocal2.get();
				// System.out.println("3333" + s);
			};
			executor.execute(new Thread(tmp));
			// semaphore.release();
		}
		
		// 创建线程
		Runnable runnable = () -> {
			
			log.info("thread ThreadLocal {}", threadLocal.get());
			log.info("thread InheritableThreadLocal {}", threadLocal1.get());
			log.info("thread TransmittableThreadLocal  {}", threadLocal2.get());
		};
		
		// 启动线程
		new Thread(runnable).start();
		executor.shutdown();
		
	}
	
}
