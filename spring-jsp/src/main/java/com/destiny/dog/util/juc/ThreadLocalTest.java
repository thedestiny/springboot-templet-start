package com.destiny.dog.util.juc;


import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class ThreadLocalTest {
	
	ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "章北海");
	
	/**
	 * ThreadLocal提供了线程独有的局部变量，可以在整个线程存活的过程中随时取用，极大地方便了一些逻辑的实现。常见的ThreadLocal用法有：
	 *
	 1 存储单个线程上下文信息。比如存储id等；
	 2 使变量线程安全。变量既然成为了每个线程内部的局部变量，自然就不会存在并发问题了；
	 3 减少参数传递。比如做一个trace工具，能够输出工程从开始到结束的整个一次处理过程中所有的信息，从而方便debug。由于需要在工程各处随时取用，可放入ThreadLocal。
	 
	 那就是在每次get()/set()/remove()ThreadLocalMap中的值的时候，会自动清理key为null的value。如此一来，value也能被回收了。
	 
	 既然对key使用弱引用，能使key自动回收，那为什么不对value使用弱引用？答案显而易见，假设往ThreadLocalMap里存了一个value，gc过后value便消失了，
	 那就无法使用ThreadLocalMap来达到存储全线程变量的效果了。
	 （但是再次访问该key的时候，依然能取到value，此时取得的value是该value的初始值。即在删除之后，如果再次访问，取到null，会重新调用初始化方法。）
	 
	 
	 内存泄漏，需要计入
	 
	 
	 * */
	
	
	public static void main(String[] args) throws InterruptedException {
		
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		InheritableThreadLocal<String> threadLocal1 = new InheritableThreadLocal<>();
		TransmittableThreadLocal<String> threadLocal2 = new TransmittableThreadLocal<>();
		
		threadLocal.set("ThreadLocal -> 外部内容");
		threadLocal1.set("InheritableThreadLocal -> 外部内容");
		threadLocal2.set("TransmittableThreadLocal -> 外部内容");
		
		AtomicLong atomicLong = new AtomicLong();
		atomicLong.compareAndSet(1,0);
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		// CountDownLatch latch = new CountDownLatch(34);
		// Semaphore semaphore = new Semaphore(3);
		// String s = threadLocal2.get();
		// System.out.println("3333" + s);
		// semaphore.release();
		
		for (int i = 0; i < 8; i++) {
			
			// semaphore.acquire(1);
			
			Runnable tmp = () -> {
				
				log.info("ThreadLocal {}", threadLocal.get());
				log.info("InheritableThreadLocal {}", threadLocal1.get());
				log.info("TransmittableThreadLocal  {}", threadLocal2.get());
				
				
			};
			executor.execute(new Thread(tmp));
			
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
