package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class PhantomReferenceTest {
	
	
	private static final List<Object> LIST = new ArrayList<>();
	private static final ReferenceQueue<Model> QUEUE = new ReferenceQueue<>();
	
	public static void main(String[] args) throws InterruptedException {
		
		ThreadLocal<String> local = new ThreadLocal<>();
		local.set("345");
		String node = local.get();
		System.out.println(node);
		
		
		PhantomReference<Model> reference = new PhantomReference<>(new Model(), QUEUE);
		
		new Thread(() -> {
			while (true) {
				LIST.add(new byte[1024 * 1024]);
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(reference.get());
			}
			
		}).start();
		
		new Thread(() -> {
			
			while (true) {
				Reference<? extends Model> poll = QUEUE.poll();
				// 管理堆外内存
				if (poll != null) {
					System.out.println(" ------- 虚引用被回收了-----" + poll);
				}
				
			}
			
		}).start();
		
		TimeUnit.SECONDS.sleep(1);
		
		
		/**
		 
		 1. 每次垃圾回收时都会被回收，主要用于监测对象是否已经从内存中删除
		 2. 虚引用必须和引用队列关联使用, 当垃圾回收器准备回收一个对象时，如果发现它还有虚引用，就会把这个虚引用加入到与之 关联的引用队列中
		 3. 程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。
		 如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动
		 Object obj = new Object();
		 PhantomReference<Object> pf = new PhantomReference<Object>(obj);
		 obj=null;
		 pf.get();//永远返回null
		 pf.isEnQueued();//返回是否从内存中已经删除
		 */
		// ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
		// 虚引用用于管理堆外内存 zero copy direct buffer netty 虚引用
		// PhantomReference<String> phantomReference = new PhantomReference<String>("233", referenceQueue);
		// String s = phantomReference.get();
		// log.info(" s is {}", s);
		// Reference<? extends String> poll = referenceQueue.poll();
		// log.info(" s is {}",poll);
		
		ReentrantLock reentrantLock = new ReentrantLock(true);
		reentrantLock.lock();
		reentrantLock.unlock();
		// await -> wait signal -> notify
		
		// ThreadLocal<String> local = new ThreadLocal<>();
		
		local.set("rrr");
		
		StampedLock stampedLock = new StampedLock();
		
		// CLH 队列锁 和 MCS 队列锁
		
		long l = stampedLock.readLock();
		long l1 = stampedLock.writeLock();
		
		long l2 = stampedLock.tryOptimisticRead();
		boolean validate = stampedLock.validate(l2);
		
		
		AtomicLong atomicLong = new AtomicLong();
		AtomicReference<Long> atomicReference = new AtomicReference<>();
		// AtomicIntegerFieldUpdater updater
	}
	
	
	/*public void optimisticRead() {
		// https://blog.csdn.net/qq_37939251/article/details/83536984
		// https://blog.csdn.net/u010512429/article/details/80314721
		long stamp = lock.tryOptimisticRead();
		int c = balance;
		// 这里可能会出现了写操作，因此要进行判断
		if(!lock.validate(stamp)) {
			// 要重新读取
			stamp = lock.readLock();
			try{
				c = balance;
			}
			finally{
				lock.unlockRead(stamp);
			}
		}
		System.out.println("读取的值为:"+c);
	}*/
	
}
