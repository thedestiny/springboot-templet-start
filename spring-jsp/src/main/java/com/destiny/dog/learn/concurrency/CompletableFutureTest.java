package com.destiny.dog.learn.concurrency;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
				TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 4));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "cf1";
		});
		
		CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(RandomUtil.randomInt(2, 4));
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
		anyOf.thenAccept(nd -> {
			System.out.print(" nd is : : ");
			System.out.println(nd);
		});
		
		
		TimeUnit.SECONDS.sleep(333);
		
		
	}
	
	@Test
	public void test001() throws Exception {
		
		// runAsync 异步执行任务，无返回值
		// supplyAsync 异步执行任务，有返回值
		// thenCombine 合并已经执行的任务1和任务2，交给任务3进行执行
		// thenApply、thenAccept、thenRun 和 thenCompose 是串行关系
		
		
		
		// 1 异步执行任务
		CompletableFuture<Void> future1 = CompletableFuture.runAsync(()->{
			log.info("异步任务第一步 runAsync ");
		});
		// 2 异步执行任务第二步
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(()->{
			log.info("异步任务第二步 runAsync ");
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "第二步结果";
		});
		
		// 3 合并执行结果 thenCombine
		CompletableFuture<String> futureRes = future1
				.thenCombine(future2, (res1, res2)->{
			log.info("合并第一步和第二步结果 thenCombine ");
			return "3:: ->" + res2;
		}).exceptionally(e -> {
			log.info("异常信息 {}",e.getMessage());
			return "异常信息";
		});
		
		log.info("最终执行结果 :: " + futureRes.get());
		
		// 2依赖1的结果,3依赖2的结果,123 串行执行
		CompletableFuture<String> f0 =
				CompletableFuture.supplyAsync(
						() -> "Hello World") //1
						.thenApply(s -> s + " QQ") //2
						.thenApply(String::toUpperCase);//3
		System.out.println(f0.join());
		
		// 汇聚关系
		// thenCombine、thenAcceptBoth 和 runAfterBoth 表示 and 关系
		// applyToEither、acceptEither 和 runAfterEither 表示 or 关系，任何一个执行完都可以进行
		
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(()->{
			log.info("异步任务第三步 runAsync ");
			return "第三步结果";
		});
		
		// applyToEither
		CompletableFuture<String> fun = future2.applyToEither(future3,(res)-> "applyToEither" + res);
		
		log.info("applyToEither join is {}",fun.join());
		log.info("applyToEither get  is {}",fun.get());
		
		// thenCombine 会把 两个 CompletionStage 的任务都执行完成后，把两个任务的结果一块交给 thenCombine 来处理。
		// future2.thenCombine()
		
	/*	CompletableFuture completableFuture = CompletableFuture.supplyAsync(() -> {
			log.info("supplyAsync value {}", "333");
			return "333";
		});
		CompletableFuture completableFuture1 = completableFuture.thenCompose(node -> {
			
			log.info("thenCompose value {}", node);
			return "444" + node;
		});
		
		System.out.println(completableFuture1.get());*/
		
		
	}
	
	
}
