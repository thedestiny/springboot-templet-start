package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static cn.hutool.core.thread.ThreadUtil.sleep;

@Slf4j
public class CompletableFutureTest {
	
	
	/**
	 * https://www.cnblogs.com/zhaof/p/5945576.html nginx
	 * <p>
	 * 用多线程优化性能，其实不过就是将串行操作变成并行操作。 当遇到某些
	 * 异步化 用多线程优化性能这个核心方案得以实施的基础
	 * CompletableFuture java8 以后提供的异步编程
	 *
	 */
	
	public static void main(String[] args) {
		
		
		//任务1：洗水壶->烧开水
		CompletableFuture<Void> f1 =
				CompletableFuture.runAsync(() -> {
					System.out.println("T1:洗水壶...");
					sleep(1, TimeUnit.SECONDS);
					
					System.out.println("T1:烧开水...");
					sleep(15, TimeUnit.SECONDS);
				});
		
		
		//任务2：洗茶壶->洗茶杯->拿茶叶
		CompletableFuture<String> f2 =
				CompletableFuture.supplyAsync(() -> {
					System.out.println("T2:洗茶壶...");
					sleep(1, TimeUnit.SECONDS);
					
					System.out.println("T2:洗茶杯...");
					sleep(2, TimeUnit.SECONDS);
					
					System.out.println("T2:拿茶叶...");
					sleep(1, TimeUnit.SECONDS);
					return "龙井";
				});
		
		
		//任务3：任务1和任务2完成后执行：泡茶
		CompletableFuture<String> f3 =
				f1.thenCombine(f2, (__, tf) -> {
					System.out.println("T1:拿到茶叶:" + tf);
					System.out.println("T1:泡茶...");
					return "上茶:" + tf;
				});
		
		//等待任务3执行结果
		System.out.println(f3.join());
		
		// runAsync()、supplyAsync()、thenCombine()
		
		//  CompletableFuture
		//  runAsync     无返回值
		//  supplyAsync  有返回值
		// 可以传入线程池
		
		// CompletionStage 接口有串行 并行 汇聚关系
		// f1.thenCombine(f2, () -> { f3 }) 表示汇聚关系
		
		// 串行关系  thenApply、thenAccept、thenRun 和 thenCompose 这四个系列的接口。
		// 并行关系
		
		CompletableFuture<String> result = CompletableFuture
				.supplyAsync(() -> {
					
					try {
						TimeUnit.SECONDS.sleep(3);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					return "aaaaa";
					
				}).thenApply(s -> s + "22")
				.thenApply(String::trim)
				.thenApply(String::toUpperCase);
		
		// CompletionStage 接口里面描述 AND 汇聚关系，
		// 主要是 thenCombine、thenAcceptBoth 和 runAfterBoth 系列的接口
		




		
	}
	
	
}
