package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.WeakReference;

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
		WeakReference<String> weakReference = new WeakReference<String>(new String("2333"));
		// 应用场景 WeakHashMap threadLocal
		String s = weakReference.get();
		log.info("s is {}", s);
		System.gc();
		s = weakReference.get();
		log.info("s is {}", s);
		s = weakReference.get();
		log.info("s is {}", s);
		s = weakReference.get();
		log.info("s is {}", s);
		s = weakReference.get();
		log.info("s is {}", s);
		s = weakReference.get();
		log.info("s is {}", s);
		
		// WeakHashMap<String, String> dd = new WeakHashMap<>();
	}
	
}
