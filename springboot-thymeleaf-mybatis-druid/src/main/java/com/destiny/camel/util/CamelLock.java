package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class CamelLock {
	
	private static Unsafe unsafe;
	private static Long stateOffset;
	
	static {
		try {
			unsafe = getUnsafe();
			// Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			// theUnsafe.setAccessible(true);
			// unsafe =  (Unsafe) theUnsafe.get(null);
			// unsafe = AccessController.doPrivileged(action);
			// stateOffset = unsafe.objectFieldOffset(CamelLock.class.getDeclaredField("stateOffset"));
			// if (unsafe == null) {
			//
			// } else {
			// 	stateOffset = 0L;
			// }
			
			
		} catch (Exception e) {
			log.info("error is {} detail is {}", e.getMessage(), e);
			throw new RuntimeException("Unable to load unsafe", e);
		}
		
	}
	
	/**
	 * 记录加锁的次数
	 */
	private static int state = 0;
	
	public static int getState() {
		return state;
	}
	
	private static ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();
	
	/**
	 * 当前线程
	 */
	private static Thread lockHolder;
	
	public static void setLockHolder(Thread lockHolder) {
		CamelLock.lockHolder = lockHolder;
	}
	
	public static Thread getLockHolder() {
		return lockHolder;
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
		return unsafe.compareAndSwapInt(this, stateOffset.longValue(), except, update);
		
	}
	
	public static void main(String[] args) throws NoSuchFieldException {
		System.out.println("22");
		// 首先是Person类中的name属性，在内存中设定的偏移位置
		Field field2 = CamelLock.class.getDeclaredField("state");
		System.out.println(unsafe);
		// 一旦这个类实例化后，该属性在内存中的偏移位置
		// long offset2 = unsafe.objectFieldOffset(field2);
		// System.out.println(offset2);
	}
	
	public static Unsafe getUnsafe() throws PrivilegedActionException {
		/*try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			//因为 Unsafe 的 theUnsafe 字段是private 的，所以这里需要设置成可访问的
			field.setAccessible(true);
			//Unsafe 的这个属性 theUnsafe 是静态的所以这里的get参数就是null
			return  (Unsafe)field.get(Unsafe.class);
			
			// return unsafe;
		} catch (Exception e) {}
		return null;*/
		
		
		PrivilegedExceptionAction<Unsafe> action = new PrivilegedExceptionAction<Unsafe>() {
			public Unsafe run() throws Exception {
				Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
				theUnsafe.setAccessible(true);
				return (Unsafe) theUnsafe.get(null);
			}
		};
		Unsafe unsafe = AccessController.doPrivileged(action);
		return unsafe;
	}
	
	// static class Instance {
	// 	public static Unsafe reflectObj() {
	// 		try {
	// 			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
	// 			theUnsafe.setAccessible(Boolean.TRUE);
	// 			return (Unsafe) theUnsafe.get(null);
	// 		} catch (Exception e) {
	// 			log.error("error !");
	// 		}
	// 		return null;
	// 	}
	// }
	
}
