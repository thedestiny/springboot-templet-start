package com.destiny.dog.util.juc;

import cn.hutool.core.util.RandomUtil;

import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		int num = 10;
		
		ExecutorService service = Executors.newFixedThreadPool(3);
		CountDownLatch latch = new CountDownLatch(num);
		Semaphore semaphore = new Semaphore(3,true);
		
		
		for (int i = 0; i < 10; i++) {
			
			Thread thread = new Thread(() -> {
				try {
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " finish!");
				latch.countDown();
				semaphore.release();
			}, "thread" + (i + 1));
			
			service.execute(thread);
			
		}
		latch.await();
		service.shutdown();
		System.out.println("executor finish !");
		
		// 执行时，必须加上虚拟机参数-XX:-RestrictContended，@Contended注释才会生效。
		HashMap<String,String> hashMap = new HashMap<>();
		hashMap.put("34","56");
		
		
		
		
	}
}
