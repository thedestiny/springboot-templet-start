package com.destiny.dog.learn.juc;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class LockSupportTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		
		Thread thread = new Thread(() -> {
			
			for (int i = 0; i < 10; i++) {
				if (i == 5) {
					LockSupport.park();
				}
				System.out.println(i);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		});
		// 如果先执行 unpark 则 后续再执行park 时会失效，和 wait 和 notify 不一样
		
		thread.start();
		TimeUnit.SECONDS.sleep(15);
		LockSupport.unpark(thread);
		
		//LockSupport.unpark(thread);
		
		Semaphore semaphore = new Semaphore(2);
		
		// wait 和 notify 只有在持有锁的线程才能调用  wait 持有锁的线程释放资源 notify 随机叫醒等待队列中的一个 notifyAll 叫醒索所有
		// 需要先 notify 然后再 wait
		
		
		ConcurrentLinkedDeque<String> linkedDeque = new ConcurrentLinkedDeque<>();
		
		LinkedTransferQueue<String> transferQueue = new LinkedTransferQueue<>();
		transferQueue.poll();
	 
		transferQueue.transfer("e");
		
		SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
		synchronousQueue.offer("3");
		synchronousQueue.put("3");
		
	}
	
	
	
}
