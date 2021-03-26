package com.destiny.dog.learn;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class TestCase {
	
	
	
	public static void main(String[] args) {
	
	
	
	}
	
	
	Thread thread1,thread2;
	@Test
	public void test(){
		
		
		thread1 = new Thread(new Runnable() {
			@Override
			@SneakyThrows
			public void run() {
				while (true){
					
					System.out.println("thread1 -> a");
					LockSupport.unpark(thread2);
					TimeUnit.SECONDS.sleep(1);
					LockSupport.park();
					
				}
			}
		});
		
		thread2 = new Thread(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				while (true){
					TimeUnit.SECONDS.sleep(1);
					LockSupport.park();
					System.out.println("thread2 -> b");
					LockSupport.unpark(thread1);
					
				}
			}
		});
		
		
		thread1.start();
		thread2.start();
		
		
	}
	
	
	
}
