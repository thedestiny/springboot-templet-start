package com.destiny.dog.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureTest {
	
	/**
	 * CompletableFuture 使用 ForkJoin 线程池来实现
	 * N threads = N CPU * U CPU * (1 + W/C)
	 * 其中：
	 * N CPU 是处理器的核的数目，可以通过 Runtime.getRuntime().availableProcessors 得到
	 * U CPU 是期望的CPU利用率（该值应该介于0和1之间）
	 * W/C是等待时间与计算时间的比率
	 * <p>
	 * 这里太啰嗦了，一般的设置线程池的大小规则是
	 * 如果服务是cpu密集型的，设置为电脑的核数
	 * 如果服务是io密集型的，设置为电脑的核数*2
	 * <p>
	 * CompletableFuture 优点
	 * 异步任务结束时，会自动回调某个对象的方法；
	 * 异步任务出错时，会自动回调某个对象的方法；
	 * 主线程设置好回调后，不再关心异步任务的执行。
	 */
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		CountDownLatch latch = new CountDownLatch(1);
		CompletableFuture<String> ft = CompletableFuture.supplyAsync(() -> {
			
			System.out.println("输出内容");
			log.info("输出");
			latch.countDown();
			return "44";
		});
		ft.thenAccept((res) -> {
			System.out.println("结果" + res);
		});
		ft.exceptionally(e -> {
			System.out.println(e.getMessage());
			return e.getMessage();
		});
		
		CompletableFuture<Integer> fff = ft.thenApplyAsync(res -> {
			System.out.println("rrr " + res);
			return 23;
		});
		
		fff.thenAccept(node -> {
			System.out.println("node is :" + node);
		});
		
		
		latch.await();
		System.out.println("程序结束");
		
		CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2,4));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "cf1";
		});
		
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2,4));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "cf2";
		});
		
		// anyOf 任何一个有返回  allOf 所有的都返回
		// xxx()：表示该方法将继续在已有的线程中执行；
		// xxxAsync()：表示将异步在线程池中执行。
		
		CompletableFuture<Object> anyOf = CompletableFuture.anyOf(cf1, cf2);
		
		System.out.println(anyOf.get());
		anyOf.thenAccept(nd ->{
			System.out.print(" nd is : : ");
			System.out.println(nd);
		});
		
		
		TimeUnit.SECONDS.sleep(333);
		
		
		
		
		
	}
	
	
}
