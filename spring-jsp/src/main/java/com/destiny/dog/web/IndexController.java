package com.destiny.dog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


@Slf4j
@Controller
public class IndexController {
	
	// WeakReference
	// https://segmentfault.com/a/1190000021047279 线程池
	
	ThreadLocal<String> local = new ThreadLocal<String>() {{
		set("1234");
	}};
	
	// 可继承的
	public static final InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
	
	/**
	 * 主界面
	 */
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home() {
		
		
		inheritableThreadLocal.set("inheritableThreadLocal");
		
		Thread thread = new Thread(() -> {
			
			String s = inheritableThreadLocal.get();
			log.info("inner local {}",s);
			
		});
		
		thread.start();
		
		
		ReentrantLock reentrantLock = new ReentrantLock();
		
		Semaphore semaphore = new Semaphore(23);
		
		AtomicLong atomicLong = new AtomicLong(2L);
		atomicLong.getAndIncrement();
		// CLH 队列
		LongAdder lss = new LongAdder();
		
		
		AtomicReference<Long> atomicReference = new AtomicReference<>();
		// atomicReference.accumulateAndGet(23L,)
		
		
		
		
		
		
		return "home";
	}
	
	
}
