package com.destiny.dog.web;

import com.destiny.dog.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;


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
	
	@GetMapping(value = "/user")
	@ResponseBody
	public User data(){
		User user = new User();
		user.setAge(34);
		user.setHeight(3);
		
		return user;
	}
	
	
	
	/**
	 * 主界面
	 */
	@RequestMapping(value = {"/", "home.html", "index.html"})
	public String home() {
		
		
		inheritableThreadLocal.set("inheritableThreadLocal");
		
		Thread thread = new Thread(() -> {
			
			String s = inheritableThreadLocal.get();
			log.info("inner local {}", s);
			
		});
		
		thread.start();
		
		
		ReentrantLock reentrantLock = new ReentrantLock();
		
		Semaphore semaphore = new Semaphore(23);
		
		AtomicLong atomicLong = new AtomicLong(2L);
		atomicLong.getAndIncrement();
		// CLH 队列
		LongAdder lss = new LongAdder();
		
	
		
		
		return "home";
	}
	
	
}
