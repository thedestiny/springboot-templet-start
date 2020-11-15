package com.destiny.camel.util;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class CamelLock {
	
	// 记录加锁的次数 0 未加锁状态 大于 0 则已经处于加锁状态
	private volatile int state = zero;
	
	// 查询当前锁状态
	private int getState() {
		return this.state;
	}
	
	/**
	 * 等待队列 cas 实现同步队列
	 */
	private ConcurrentLinkedDeque<Thread> linkedDeque = new ConcurrentLinkedDeque<Thread>();
	
	/**
	 * 当前线程
	 */
	private Thread lockHolder;
	
	// 获取当前获取锁的线程
	private Thread getLockHolder() {
		return lockHolder;
	}
	
	// 设置当前拥有锁的线程
	private void setLockHolder(Thread lockHolder) {
		this.lockHolder = lockHolder;
	}
	
	// 加锁操作
	public void lock() {
		// 尝试获取一把锁
		if (acquire()) {
			return;
		}
		// 未获取到锁则将当前线程加入到等待队列中
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
	
	// 获取锁
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
			setLockHolder(null); // 唤醒队列中的一个线程继续获取锁
			Thread peek = linkedDeque.peek();
			if (ObjectUtil.isNotEmpty(peek)) {
				LockSupport.unpark(peek);
			}
		}
	}
	
	/**
	 * 常量 0
	 */
	private static final int zero = 0;
	
	// 反射方法拿到 unsafe 操作类
	private static final Unsafe unnsafe = UnsafeInstance.reflectGetUnsafeObj();
	
	private static final long sateOffset;
	
	static {
		
		try {
			// 获取 state 的内存地址偏移量
			sateOffset = unnsafe.objectFieldOffset(CamelLock.class.getDeclaredField("state"));
		} catch (Exception e) {
			log.info("e is ", e);
			throw new RuntimeException(e.getMessage());
		}
		
	}
	
	// 比较交换更新值
	public final boolean cas(int except, int update) {
		return unnsafe.compareAndSwapInt(this, sateOffset, except, update);
	}
	
	
	static Integer num = 0;
	
	public static void main(String[] args) throws InterruptedException {
		
		CountDownLatch latch = new CountDownLatch(1000);
		CamelLock lock = new CamelLock();
		
		for (int i = 0; i < 1000; i++) {
			
			new Thread(() -> {
				lock.lock();
				try {
					num += 1;
				} catch (Exception e) {
				} finally {
					latch.countDown();
					lock.unlock();
				}
				
			}).start();
		}
		// 等待所有任务结果并打印最终结果
		latch.await();
		System.out.println(num);
	}
}
