package com.destiny.dog.learn.example;

import org.junit.Test;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadInteractiveOutputTest {
	
	
	
	Thread t1 = null, t2 = null;
	
	@Test
	public void test001() {
		
		/**
		 * LockSupport 方式实现线程交替打印
		 * ReentranLock 实现交替打印线程   await signal
		 * synchronized lock 等待队列
		 * 非公平
		 * 随机唤醒
		 *
		 *
		 * */
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();
		
		
		t1 = new Thread(() -> {
			
			for (int i = 0, len = aI.length; i < len; i++) {
				
				System.out.print(aI[i] + "->");
				LockSupport.unpark(t2);
				LockSupport.park();
				
			}
		});
		t2 = new Thread(() -> {
			
			for (char node : aC) {
				
				LockSupport.park();
				System.out.print(node + "->");
				LockSupport.unpark(t1);
				
			}
		});
		
		t1.start();
		t2.start();
		
	}
	
	
	@Test
	public void test002() {
		// 锁对象
		Object obj = new Object();
		
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();
		
		Thread t1 = new Thread(() -> {
			synchronized (obj) {
				for (char node :  aI) {
					System.out.println(node + "->");
					try {
						obj.notify();
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				obj.notify();
			}
		});
		
		Thread t2 = new Thread(() -> {
			synchronized (obj) {
				for (char node : aC) {
					System.out.println(node + "->");
					try {
						obj.notify();
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				obj.notify();
			}
			
		});
		
		t1.start();
		t2.start();
	}
	
	@Test
	public void test003() {
		
		// lock 里面可以有多个队列
		
		ReentrantLock lock = new ReentrantLock();
		
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		Condition condition3 = lock.newCondition();
		
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();
		char[] aA = "HIJKLMN".toCharArray();
		
		
		Thread t1 = new Thread(() -> {
			lock.lock();
			
			try {
				
				for (char node : aI) {
					System.out.print(node);
					condition2.signal();
					condition1.await();
					
				}
				condition2.signal();
				
			} catch (Exception e) {
			
			} finally {
				lock.unlock();
			}
			
		}, "t1");
		
		Thread t2 = new Thread(() -> {
			lock.lock();
			
			try {
				
				for (char node : aC) {
					System.out.print(node);
					condition3.signal();
					condition2.await();
				}
				condition3.signal();
			} catch (Exception e) {
			
			} finally {
				lock.unlock();
			}
			
		}, "t2");
		
		Thread t3 = new Thread(() -> {
			lock.lock();
			
			try {
				
				for (char node : aA) {
					System.out.print(node);
					condition1.signal();
					condition3.await();
				}
				condition1.signal();
			} catch (Exception e) {
			
			} finally {
				lock.unlock();
			}
			
		}, "t3");
		
		t1.start();
		t2.start();
		t3.start();
		
		/**
		 * LinkedTransferQueue是一个由链表结构组成的无界阻塞TransferQueue队列。相对于其他阻塞队列，LinkedTransferQueue多了tryTransfer和transfer方法。
		 *
		 * LinkedTransferQueue采用一种预占模式。意思就是消费者线程取元素时，如果队列不为空，则直接取走数据，若队列为空，那就生成一个节点（节点元素为null）入队，
		 * 然后消费者线程被等待在这个节点上，后面生产者线程入队时发现有一个元素为null的节点，生产者线程就不入队了，直接就将元素填充到该节点，并唤醒该节点等待的线程，
		 * 被唤醒的消费者线程取走元素，从调用的方法返回。我们称这种节点操作为“匹配”方式。
		 *
		 * LinkedTransferQueue是ConcurrentLinkedQueue、SynchronousQueue（公平模式下转交元素）、LinkedBlockingQueue（阻塞Queue的基本方法）的超集。
		 * 而且LinkedTransferQueue更好用，因为它不仅仅综合了这几个类的功能，同时也提供了更高效的实现。
		 *
		 * join 把当t1 加入到当前线程
		 * */
		//
		TransferQueue<Character> transferQueue = new LinkedTransferQueue<>();
		
		ConcurrentLinkedQueue<String> linkedQueue = new ConcurrentLinkedQueue<>();
		
		
		// transferQueue.transfer(new Character((char)3));
		
		
	}
	
	@Test
	public void test004() throws InterruptedException {
		// 锁对象
		Object obj = new Object();
		
		CountDownLatch latch = new CountDownLatch(1);
		
		char[] aI = "1234567".toCharArray();
		char[] aC = "ABCDEFG".toCharArray();
		
		
		Thread t1 = new Thread(() -> {
			
			synchronized (obj) {
				
				latch.countDown();
				for (int i = 0, len = aI.length; i < len; i++) {
					
					System.out.println(aI[i] + "->");
					try {
						obj.notify();
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				obj.notify();
			}
		});
		Thread t2 = new Thread(() -> {
			
			
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			synchronized (obj) {
				
				for (char node : aC) {
					
					System.out.println(node + "->");
					try {
						obj.notify();
						obj.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
				obj.notify();
			}
			
			
		});
		
		// t1 先开始 t2 后开始
		t2.start();
		
		TimeUnit.SECONDS.sleep(3);
		
		t1.start();
		
		
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2,3,4, TimeUnit.SECONDS,new SynchronousQueue());
		
	}
	
}
