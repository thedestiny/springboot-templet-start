package com.destiny.wolf.util;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ThreadPoolTest {
	
	
	/**
	 * jdk1.8 之后新增 CompletionService
	 * CompletionService的实现目标是任务先完成可优先获取到，即结果按照完成先后顺序排序。而不是按照任务的提交顺序
	 * https://www.cnblogs.com/shijiaqi1066/p/10454237.html
	 */
	@Test
	public void test001() throws ExecutionException, InterruptedException {
		
		int corePoolSize = 2;
		int maxPoolSize = 4;
		int keepAlive = 200;
		
		int taskCount = 10;
		
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200),
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
		
		CompletionService<Integer> service = new ExecutorCompletionService<Integer>(executor);
		
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		
		for (int i = 0; i < taskCount; i++) {
			final int temp = i;
			
			Callable<Integer> callable = () -> {
				TimeUnit.MILLISECONDS.sleep(temp * 100 % 600);
				return temp * 100;
			};
			
			Future<Integer> submit = service.submit(callable);
			list.add(submit);
		}
		
		for (int i = 0; i < taskCount; i++) {
			Integer result = service.take().get();
			System.out.println("result is :: " + result);
		}
		
		for(Future<Integer> future : list){
			future.cancel(true);
		}
		
		
		executor.shutdown();
	}
	
	@Test
	public void test002() throws ExecutionException, InterruptedException {
		
		int corePoolSize = 2;
		int maxPoolSize = 4;
		int keepAlive = 200;
		
		int taskCount = 10;
		
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAlive,
				TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(200),
				Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardOldestPolicy());
		
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		
		for (int i = 0; i < taskCount; i++) {
			final int temp = i;
			
			Callable<Integer> callable = () -> {
				TimeUnit.MILLISECONDS.sleep(temp * 100 % 60);
				return temp * 100;
			};
			
			Future<Integer> submit = executor.submit(callable);
			list.add(submit);
		}
		
		for (int i = 0; i < list.size(); i++) {
			Integer result = list.get(i).get();
			System.out.println("result is :: " + result);
		}
		
		executor.shutdown();
	}
	
}
