package com.destiny.dog.learn.juc;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CompletionServiceTest {
	
	
	public static void main(String[] args) throws Exception {
		
		int taskNum = 10; // 指定任务数
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 300,
				TimeUnit.SECONDS, new LinkedBlockingQueue<>(30));
		
		CompletionService<Integer> service = new ExecutorCompletionService<>(executor);
		CountDownLatch latch = new CountDownLatch(taskNum);
		
		for (int i = 0; i < taskNum; i++) {
		   Callable<Integer> callable = () -> {
			   try {
				   int sleep = RandomUtil.randomInt(1000, 10000);
				   TimeUnit.MILLISECONDS.sleep(sleep); // 随机休眠
				   return sleep;
			   } catch (InterruptedException e) {
				   return -1;  // 发生错误返回 -1
			   } finally {
				   latch.countDown(); // 计数器 -1
			   }
		   };
		   // 执行任务
		   service.submit(callable);
		}
		
		for (int i = 0; i < taskNum; i++) {
			Future<Integer> node = service.take();
			if (node != null) {
				System.out.println("休眠时间为:" + node.get());
			}
		}
		latch.await(); // 保证所有任务都执行完毕
		executor.shutdown(); // 关闭线程池
	
		ThreadLocal local = new ThreadLocal();
		
	}
	
	
}
