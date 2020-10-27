package com.destiny.dog.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class JavaReferenceTest {
	
	
	public static void main(String[] args) {
		
		
		AtomicReference<Long> atomicReference = new AtomicReference<>();
		// atomicReference.accumulateAndGet(23L,)
		
		
		// 强软弱虚
		Object obj = new Object();
		
		/**
		 1. 内存溢出之前进行回收，GC时内存不足时回收，如果内存足够就不回收
		 2. 使用场景：在内存足够的情况下进行缓存，提升速度，内存不足时JVM自动回收
		 Object obj = new Object();
		 SoftReference<Object> sf = new SoftReference<Object>(obj);
		 sf.get();//有时候会返回null
		 3. 可以和引用队列ReferenceQueue联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
		 * */
		SoftReference<String> softReference = new SoftReference<String>(new String("2333"));
		
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
		WeakReference<String> weakReference = new WeakReference<String>(new String("123"));
		
		WeakHashMap<String, String> dd = new WeakHashMap<>();
		
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
		ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
		PhantomReference<String> phantomReference = new PhantomReference<String>("233", referenceQueue);
		String s = phantomReference.get();
		Reference<? extends String> poll = referenceQueue.poll();
		
		
		
	}
	
	
}
