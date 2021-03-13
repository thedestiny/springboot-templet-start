package com.destiny.dog.learn.juc;

import cn.hutool.core.util.RandomUtil;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

public class StampLockTest {
	

	
	
	public static void main(String[] args) throws InterruptedException {
		
		
		// writeConvert();
		
		write();
		read();
		write();
		
	
	}
	
	/**
	 * 写锁、悲观读锁和乐观读。
	 */
	private static String content = "";
	
	static StampedLock stampedLock = new StampedLock();
	
	public static void writeConvert() throws InterruptedException {
		
		// 读锁（这里可用乐观锁替代）
		long stamp = stampedLock.readLock();
		TimeUnit.MILLISECONDS.sleep(500); // 模拟io
		try {
			//循环，检查当前状态是否符合
			while (!stampedLock.validate(stamp)) {
				long ws = stampedLock.tryConvertToWriteLock(stamp);
				//如果写锁成功
				if (ws != 0L) {
					stamp = ws;// 替换stamp为写锁戳
					content += RandomUtil.randomNumbers(4);
					System.out.println("写后内容是:" + content);
					break;
				}
				//转换为写锁失败
				else {
					//释放读锁
					stampedLock.unlockRead(stamp);
					//获取写锁（必要情况下阻塞一直到获取写锁成功）
					stamp = stampedLock.writeLock();
				}
			}
		} finally {
			//释放锁（可能是读/写锁）
			stampedLock.unlock(stamp);
		}
	}
	
	public static void write() {
		// stampedLock调用 writeLock和unlockWrite时候都会导致stampedLock的stamp值的变化即每次+1，直到加到最大值，然后从0重新开始
		long stamp = stampedLock.writeLock(); //写锁
		try {
			content += RandomUtil.randomNumbers(3);
			System.out.println("写后内容是:" + content);
		} finally {
			stampedLock.unlockWrite(stamp);//释放写锁
		}
	}
	
	public static void read() throws InterruptedException {
		
		// tryOptimisticRead 是一个乐观的读,每次读的时候返回一个 stamp 值用后续做校验
		long stamp = stampedLock.tryOptimisticRead();
		// 模拟读 休眠 200ms
		TimeUnit.MILLISECONDS.sleep(200);
		// 读取内容值
		String cnt = content;
		// 如果读取过程中发生了写操作, 则需要重新读取 validate() 方法就是校验当前stamp 和 获取乐观锁的 stamp 是否一致
		if (!stampedLock.validate(stamp)) { //如果验证失败则内容发生了改变
			stamp = stampedLock.readLock(); //悲观读锁
			try {
				cnt = content;
			} finally {
				stampedLock.unlockRead(stamp);//释放读锁
			}
		}
		System.out.println("读后内容是:" + cnt);
		
	}
	
	
}


