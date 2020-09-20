package com.destiny.wolf.util;


import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

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
		
		for (Future<Integer> future : list) {
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
				System.out.println("temp is :" + temp);
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
	
	
	@Test
	public void test003() throws InterruptedException {
		
		// 任务数量
		int num = 10;
		// 采用 guava 提供的的线程池工厂builder
		final ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("task-test-%d")
				.setDaemon(true)
				.build();
		
		ExecutorService service = Executors.newFixedThreadPool(4, threadFactory);
		// 运行 10 次任务后结束
		CountDownLatch latch = new CountDownLatch(num);
		// 信号量 每次并行3个
		Semaphore semaphore = new Semaphore(1);
		AtomicLong atomicLong = new AtomicLong(0L);
		
		for (int i = 0; i < num; i++) {
			
			TaskDemo task = new TaskDemo(i * 300, atomicLong, latch, semaphore);
			service.execute(task);
		}
		
		latch.await();
		System.out.println(atomicLong.get());
		
	}
	
	@Test
	public void test004() throws InterruptedException {
		
		// 任务数量
		int num = 100;
		// 采用 guava 提供的的线程池工厂builder
		final ThreadFactory threadFactory = new ThreadFactoryBuilder()
				.setNameFormat("task-test-%d")
				.setDaemon(true)
				.build();
		
		ExecutorService service = Executors.newFixedThreadPool(4, threadFactory);
		
		ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
		
		// 运行 10 次任务后结束
		CyclicBarrier barrier = new CyclicBarrier(5, () -> {
			
			for (Map.Entry<String, Integer> entry : map.entrySet()) {
				log.info(" map thread id is {} and count is {}", entry.getKey(), entry.getValue());
			}
			log.info(" cycle stage end  ");
		});
		
		for (int i = 0; i < num; i++) {
			
			TaskCycDemo demo = new TaskCycDemo(map, barrier);
			service.execute(demo);
		}
		
		boolean b = service.awaitTermination(1000, TimeUnit.SECONDS);
		
		
	}
	
	
	@Slf4j
	public static class TaskCycDemo implements Runnable {
		
		private ConcurrentHashMap<String, Integer> map;
		
		private CyclicBarrier barrier;
		
		public TaskCycDemo(ConcurrentHashMap<String, Integer> map, CyclicBarrier barrier) {
			this.map = map;
			this.barrier = barrier;
		}
		
		@SneakyThrows
		@Override
		public void run() {
			
			long id = Thread.currentThread().getId();
			if (map.get(id + "") != null) {
				Integer val = map.get(id + "") + 10;
				map.put(id + "", val);
			} else {
				map.put(id + "", 10);
			}
			
			log.info("current thread name {} is waiting !", Thread.currentThread().getName());
			barrier.await();
			log.info("current thread name {} is working !", Thread.currentThread().getName());
			
		}
		
		
	}
	
	
	@Slf4j
	public static class TaskDemo implements Runnable {
		
		
		private int tmp;
		private AtomicLong aLong;
		private CountDownLatch latch;
		private Semaphore semaphore;
		
		public TaskDemo(int tmp, AtomicLong atomicLong, CountDownLatch latch, Semaphore semaphore) {
			this.tmp = tmp;
			this.aLong = atomicLong;
			this.latch = latch;
			this.semaphore = semaphore;
		}
		
		@SneakyThrows
		@Override
		public void run() {
			semaphore.acquire();
			int tim = tmp + RandomUtil.randomInt(3000);
			
			TimeUnit.MILLISECONDS.sleep(tim);
			
			for (int i = 0; i < 10; i++) {
				aLong.getAndIncrement();
			}
			
			latch.countDown();
			semaphore.release(1);
			log.info("execute time :: {}", tim);
		}
	}
	
}
