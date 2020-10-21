package com.destiny.lobster.utils.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class CamelLock {
	
	/**
	 * 记录加锁的次数
	 */
	private volatile int state = 0;
	
	public int getState() {
		return this.state;
	}
	
	private ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();
	
	/**
	 * 当前线程
	 */
	private Thread lockHolder;
	
	public Thread getLockHolder() {
		return lockHolder;
	}
	
	public void setLockHolder(Thread lockHolder) {
		this.lockHolder = lockHolder;
	}
	
	public void lock() {
		if (acquire()) {
			return;
		}
		
		Thread thread = Thread.currentThread();
		linkedDeque.offer(thread);
		
		for (; ; ) {
			// 让出 cpu 使用权 Thread.yield();
			if (thread == linkedDeque.peek() && acquire()) {
				linkedDeque.poll(); // 队列中移出
				break;
			}
			
			LockSupport.park(thread);
		}
	}
	
	public void unlock() {
		
		// wait 和 notify 唤醒是随机的，不能准确唤醒一个线程
		if (Thread.currentThread() != getLockHolder()) {
			throw new RuntimeException("解锁失败");
		}
		int state = getState();
		if (cas(state, 0)) {
			setLockHolder(null);
			Thread peek = linkedDeque.peek();
			if (peek != null) {
				LockSupport.unpark(peek);
			}
		}
		
		
	}
	
	private boolean acquire() {
		Thread thread = Thread.currentThread();
		int state = getState();
		if (state == 0) {
			if ((linkedDeque.size() == 0 || thread == linkedDeque.peek()) && cas(0, 1)) {
				setLockHolder(thread);
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	public final boolean cas(int except, int update) {
		try {
			Unsafe unsafe = reflectGetUnsafe();
			long valueOffset = unsafe.objectFieldOffset
					(CamelLock.class.getDeclaredField("state"));
			return unsafe.compareAndSwapInt(state, valueOffset, except, update);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void main(String[] args) throws NoSuchFieldException {
		
		CamelLock lock = new CamelLock();
		lock.lock();
		
		lock.unlock();
		
		LinkedBlockingQueue queue = new LinkedBlockingQueue();
		
		
	}
	
	private static Unsafe reflectGetUnsafe() {
		try {
			Class<?> clazz = Unsafe.class;
			Field field = clazz.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(clazz);
		} catch (Exception e) {
			System.out.println(" err is  ->  :" + e.getMessage());
			return null;
		}
	}
	
}
