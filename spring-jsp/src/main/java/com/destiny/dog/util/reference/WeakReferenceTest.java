package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class WeakReferenceTest {
	
	public static void main(String[] args) {
		
		/**
		 1. 每次GC时回收，无论内存是否足够
		 2. 使用场景：a. ThreadLocalMap防止内存泄漏  b. 监控对象是否将要被回收
		 Object obj = new Object();
		 WeakReference<Object> wf = new WeakReference<Object>(obj);
		 wf.get();//有时候会返回null
		 wf.isEnQueued();//返回是否被垃圾回收器标记为即将回收的垃圾
		 System.gc();  //通知JVM的gc进行垃圾回收，但JVM不一定会立刻执行
		 wf.get();//此时会返回null
		 3. 弱引用可以和一个引用队列（ReferenceQueue）联合使用，如果弱引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
		 * */
		WeakReference<Model> weakReference = new WeakReference<>(new Model());
		
		ReentrantLock reentrantLock = new ReentrantLock(true);
		
		AtomicLong atomicLong = new AtomicLong();
		atomicLong.set(20);
		atomicLong.compareAndSet(20,45);
		// atomicLong.weakCompareAndSet(30);
		long dd = atomicLong.addAndGet(23);
		System.out.println(dd);
		
		// 应用场景 WeakHashMap threadLocal
		Model s = weakReference.get();
		log.info("s is {}", s == null);
		byte[] rr = new byte[3 * 1024 * 1024];
		System.gc();
		s = weakReference.get();
		System.gc();
		log.info("s is {}", s == null);
		byte[] rr1 = new byte[5 * 1024 * 1024];
		System.gc();
		s = weakReference.get();
		log.info("s is {}", s == null);
		System.gc();
		byte[] rr2 = new byte[5 * 1024 * 1024];
	
		s = weakReference.get();
		
		log.info("s is {}", s == null);
		s = weakReference.get();
		System.gc();
		log.info("s is {}", s == null);
		s = weakReference.get();
		log.info("s is {}", s == null);
		
		// WeakHashMap<String, String> dd = new WeakHashMap<>();
	}
	
}
