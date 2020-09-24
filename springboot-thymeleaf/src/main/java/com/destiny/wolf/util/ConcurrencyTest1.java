package com.destiny.wolf.util;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ConcurrencyTest1 {
	
	
	public static void main(String[] args) {
		
		
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10, false);
		
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 300, TimeUnit.SECONDS, queue);
		
		Faker faker = new Faker(Locale.CHINA);
		
		for (int i = 0; i < 20; i++) {
			Runnable run = () -> {
				long l = System.currentTimeMillis();
				Thread thread = Thread.currentThread();
				System.out.println(String.format("thread name %s -> %d :: %s", thread.getName(),l, faker.name().fullName()));
			};
			executor.execute(run);
		}
		
		executor.shutdown();
		
	}
	
	
}
