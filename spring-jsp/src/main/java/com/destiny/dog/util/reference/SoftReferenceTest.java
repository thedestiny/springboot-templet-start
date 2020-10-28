package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.SoftReference;

@Slf4j
public class SoftReferenceTest {
	
	public static void main(String[] args) {
		
		/**
		 1. 内存溢出之前进行回收，GC时内存不足时回收，如果内存足够就不回收
		 2. 使用场景：在内存足够的情况下进行缓存，提升速度，内存不足时JVM自动回收
		 Object obj = new Object();
		 SoftReference<Object> sf = new SoftReference<Object>(obj);
		 sf.get();//有时候会返回null
		 3. 可以和引用队列ReferenceQueue联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
		 * */
		SoftReference<String> softReference = new SoftReference<String>(new String("2333"));
		
		String s = softReference.get();
		log.info("s is {}",s);
		System.gc();
		s = softReference.get();
		log.info("s is {}",s);
		s = softReference.get();
		log.info("s is {}",s);
		s = softReference.get();
		log.info("s is {}",s);
		s = softReference.get();
		log.info("s is {}",s);
		s = softReference.get();
		log.info("s is {}",s);
		
	}
	
}
