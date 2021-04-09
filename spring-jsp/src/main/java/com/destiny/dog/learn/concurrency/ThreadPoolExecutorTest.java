package com.destiny.dog.learn.concurrency;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.atomic.LongAdder;

public class ThreadPoolExecutorTest {
	
	public static final String OS_NAME = System.getProperty("os.name");
	
	public static void main(String[] args) {
		
		System.out.println("OS_NAME  " + OS_NAME);
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setThreadNamePrefix("app-");
		
		AtomicLong atomicLong = new AtomicLong();
		atomicLong.set(34L);
		AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(20, 1);
		int stamp = stampedReference.getStamp();
		Integer reference = stampedReference.getReference();
		boolean falg = stampedReference.compareAndSet(reference, 30, stamp, 2);
		
		LongAdder longAdder = new LongAdder();
		longAdder.add(34);
		longAdder.increment();
		longAdder.longValue();
		
	}
	
}
