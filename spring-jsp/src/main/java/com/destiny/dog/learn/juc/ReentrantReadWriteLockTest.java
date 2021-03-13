package com.destiny.dog.learn.juc;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
public class ReentrantReadWriteLockTest {
	
	// private static int value;
	
	// 使用 ReentrantLock
	static Lock lock = new ReentrantLock();
	// 使用 ReentrantReadWriteLock
	static ReentrantReadWriteLock lock1 = new ReentrantReadWriteLock();
	static Lock readLock = lock1.readLock();
	static Lock writeLock = lock1.writeLock();
	
	public static void read(Lock lock) {
		try {
			lock.lock();
			TimeUnit.SECONDS.sleep(1);
			System.out.println(getTime() + " 读完了");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			lock.unlock();
		}
	}
	
	public static void write(Lock lock) {
		try {
			lock.lock();
			TimeUnit.SECONDS.sleep(5);
			
			System.out.println(getTime() + " 写完了");
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			lock.unlock();
		}
	}
	
	public static String getTime() {
		DateTime dateTime = new DateTime();
		return dateTime.toString("yyyy-MM-dd-HH:mm:ss.SSS");
	}
	
	public static void main(String[] args) {
		
		
		Runnable read = () -> {
			// read(lock);
			read(readLock);
		};
		
		Runnable write = () -> {
			// write(lock);
			write(writeLock);
		};
		
		
		for (int i = 0; i < 3; i++) {
			new Thread(read).start();
		}
		for (int i = 0; i < 1; i++) {
			new Thread(write).start();
		}
	}
	
	
}
