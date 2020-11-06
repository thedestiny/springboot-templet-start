package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SoftReferenceTest {
	
	private long value = 0;
	private long l1,l2,l3,l7;
	
	// long 8 *8 = 64
	
	public static void main(String[] args) throws InterruptedException {
		
		/**
		 * -Xmx20m -XX:-DoEscapeAnalysis
		 1. 内存溢出之前进行回收，GC时内存不足时回收，如果内存足够就不回收
		 2. 使用场景：在内存足够的情况下进行缓存，提升速度，内存不足时JVM自动回收
		 Object obj = new Object();
		 SoftReference<Object> sf = new SoftReference<Object>(obj);
		 sf.get();//有时候会返回null
		 3. 可以和引用队列ReferenceQueue联合使用，如果软引用所引用的对象被JVM回收，这个软引用就会被加入到与之关联的引用队列中
		 * */
		
		ReferenceQueue<byte[]> queue = new ReferenceQueue<>();
		
		SoftReference<byte[]> softReference = new SoftReference<>(new byte[3 * 1024 * 1024], queue);
		
		new Thread(()->{
			
			while (true){
				Reference<? extends byte[]> poll = queue.poll();
				if (poll != null) {
					System.out.println("对象还在");
				}else {
					System.out.println("对象已经回收");
				}
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
		
		
		// byte[] s = softReference.get();
		System.out.println(softReference.get());
		System.gc();
		TimeUnit.SECONDS.sleep(1);
		System.out.println(softReference.get());
		System.gc();
		// TimeUnit.SECONDS.sleep(1);
		byte[] tmp = new byte[10 * 1024 * 1024];
		System.out.println(tmp);
		System.gc();
		System.out.println(softReference.get());
		System.out.println(softReference.get());
		
		//  软引用 内存不够时回收  缓存的使用场景
		//  弱引用 发生gc 时回收
		//  虚引用 get 时已经回收 管理堆外内存 zero copy DirectByteBuffer
		
	}
	
}
