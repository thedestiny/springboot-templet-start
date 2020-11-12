package com.destiny.dog.util.reference;

import lombok.extern.slf4j.Slf4j;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.StampedLock;

@Slf4j
public class PhantomReferenceTest1 {
	
	
	static class Node{
		private byte[] rr = new byte[4 * 1024 * 1024];
		@Override
		protected void finalize() {
			System.out.println("对象被回收");
		}
	}
	
	private static final List<Object> list = new ArrayList<>();
	private static final ReferenceQueue<Node> referenceQueue = new ReferenceQueue<>();
	
	public static void main(String[] args) throws InterruptedException {
		
		Node node = new Node();
		PhantomReference<Node> reference = new PhantomReference<>(node, referenceQueue);
		
		new Thread(() -> {
			while (true) {
				list.add(new byte[1024 * 1024]);
				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(reference.get());
			}
			
		}).start();
		
		new Thread(() -> {
			
			while (true) {
				Reference<? extends Node> poll = referenceQueue.poll();
				// 管理堆外内存
				if (poll != null) {
					System.out.println(" ------- 虚引用被回收了-----" + poll);
				}
				try {
					TimeUnit.MILLISECONDS.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}).start();
	}
	
}
