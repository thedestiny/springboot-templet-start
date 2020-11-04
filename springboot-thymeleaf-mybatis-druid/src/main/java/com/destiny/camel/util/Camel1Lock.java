//package com.destiny.camel.util;
//
//import lombok.extern.slf4j.Slf4j;
//import sun.misc.Unsafe;
//
//import java.lang.reflect.Field;
//import java.util.concurrent.ConcurrentLinkedDeque;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.locks.LockSupport;
//
//@Slf4j
//public class Camel1Lock {
//
//	/**
//	 * 记录加锁的次数
//	 */
//	private volatile int state = 0;
//
//	public int getState() {
//		return this.state;
//	}
//
//	private ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();
//
//	/**
//	 * 当前线程
//	 */
//	private Thread lockHolder;
//
//	public Thread getLockHolder() {
//		return lockHolder;
//	}
//
//	public void setLockHolder(Thread lockHolder) {
//		this.lockHolder = lockHolder;
//	}
//
//	public void lock() {
//
//		if (acquire()) {
//			return;
//		}
//
//		Thread thread = Thread.currentThread();
//		linkedDeque.offer(thread);
//
//		for (; ; ) {
//			// 让出 cpu 使用权 Thread.yield();
//			if (thread == linkedDeque.peek() && acquire()) {
//				linkedDeque.poll(); // 队列中移出
//				break;
//			}
//
//			LockSupport.park(thread);
//		}
//	}
//
//	public void unlock() {
//
//		// wait 和 notify 唤醒是随机的，不能准确唤醒一个线程
//		if (Thread.currentThread() != getLockHolder()) {
//			throw new RuntimeException("解锁失败");
//		}
//		int state = getState();
//		if (cas(state, 0)) {
//			setLockHolder(null);
//			Thread peek = linkedDeque.peek();
//			if (peek != null) {
//				LockSupport.unpark(peek);
//			}
//		}
//
//
//	}
//
//	private boolean acquire() {
//		Thread thread = Thread.currentThread();
//		int state = getState();
//		if (state == 0) {
//			if ((linkedDeque.size() == 0 || thread == linkedDeque.peek()) && cas(0, 1)) {
//				setLockHolder(thread);
//				return Boolean.TRUE;
//			}
//		}
//
//		return Boolean.FALSE;
//	}
//
//	private static Unsafe reflectGetUnsafe() {
//		try {
//			Class<?> clazz = Unsafe.class;
//			Field field = clazz.getDeclaredField("theUnsafe");
//			field.setAccessible(true);
//			return (Unsafe) field.get(clazz);
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			return null;
//		}
//	}
//
//	public final boolean cas(int except, int update) {
//		try {
//			Unsafe usf = reflectGetUnsafe();
//			long valueOffset = usf.objectFieldOffset
//					(Camel1Lock.class.getDeclaredField("state"));
//			return usf.compareAndSwapInt(state, valueOffset, except, update);
//
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//	}
//
//	public static void main(String[] args) throws NoSuchFieldException {
//
//		Camel1Lock lock = new Camel1Lock();
//		lock.lock();
//
//		lock.unlock();
//
//		lock.lock();
//
//		lock.unlock();
//
//		LinkedBlockingQueue queue = new LinkedBlockingQueue();
//
//
//	}
//
//
//
//}
