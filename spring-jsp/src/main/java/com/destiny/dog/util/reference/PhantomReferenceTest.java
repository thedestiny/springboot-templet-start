package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

@Slf4j
public class PhantomReferenceTest {
	
	
	public static void main(String[] args) {
		
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
		// 虚引用用于管理堆外内存 zero copy direct buffer netty 虚引用
		PhantomReference<String> phantomReference = new PhantomReference<String>("233", referenceQueue);
		String s = phantomReference.get();
		log.info(" s is {}", s);
		Reference<? extends String> poll = referenceQueue.poll();
		log.info(" s is {}",poll);
		
	}
	
	
}
