package com.destiny.camel.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class CamelLock {
	
	/**
	 * 记录加锁的次数
	 */
	private volatile int state = zero;
	
	private int getState() {
		return this.state;
	}
	
	/**
	 * 等待队列
	 */
	private ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();
	
	/**
	 * 当前线程
	 */
	private Thread lockHolder;
	
	private Thread getLockHolder() {
		return lockHolder;
	}
	
	private void setLockHolder(Thread lockHolder) {
		this.lockHolder = lockHolder;
	}
	
	public void lock() {
		
		if (acquire()) {
			return;
		}
		
		Thread thread = Thread.currentThread();
		linkedDeque.offer(thread);
		
		for (; ; ) {
			// 让出 cpu 使用权 Thread.yield(); 锁需要重入
			if (thread == linkedDeque.peek() && acquire()) {
				linkedDeque.poll(); // 队列中移出
				break;
			}
			// 释放cpu 使用权 从 cpu 中的数据转移到内存中,即不占用 cpu 资源
			LockSupport.park(thread);
		}
	}
	
	private boolean acquire() {
		Thread thread = Thread.currentThread();
		int state = getState(); // 查询当前 state 的状态
		if (state == 0) {
			// 队列为空 或者 当前队列中只有自己
			if ((linkedDeque.size() == 0 || thread == linkedDeque.peek()) && cas(state, state + 1)) {
				setLockHolder(thread);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}
	
	public void unlock() {
		
		// wait 和 notify 唤醒是随机的，不能准确唤醒一个线程
		if (Thread.currentThread() != getLockHolder()) {
			throw new RuntimeException("解锁失败");
		}
		int state = getState();
		if (cas(state, zero)) {
			setLockHolder(null);
			Thread peek = linkedDeque.peek();
			if (ObjectUtil.isNotEmpty(peek)) {
				LockSupport.unpark(peek);
			}
		}
	}
	/**
	 * 常量 0
	 * */
	private static final int zero = 0;
	
	
	private static final Unsafe unnsafe = UnsafeInstance.reflectGetUnsafeObj();
	
	private static final long sateOffset;
	
	static {
		
		try {
			sateOffset = unnsafe.objectFieldOffset(CamelLock.class.getDeclaredField("state"));
		} catch (Exception e) {
			log.info("e is ", e);
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	
	public final boolean cas(int except, int update) {
		return unnsafe.compareAndSwapInt(this, sateOffset, except, update);
	}
	
	
	
}
