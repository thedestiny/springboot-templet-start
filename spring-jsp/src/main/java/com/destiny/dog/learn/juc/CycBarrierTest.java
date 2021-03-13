package com.destiny.dog.learn.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

@Slf4j
public class CycBarrierTest {
	
	
	static class TaskThread extends Thread {
		
		CyclicBarrier barrier;
		
		public TaskThread(CyclicBarrier barrier, Integer num) {
			super("运动员" + num);
			this.barrier = barrier;
		}
		
		@Override
		public void run() {
			try {
				
				for (int i = 0; i < 10 ; i++) {
					Thread.sleep(1000);
					System.out.println(getName() + " 到达起点");
					Thread.sleep(new Random().nextInt(300));
					barrier.await();
					// System.out.println(getName() + " 到达终点");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Integer threadNum = 5;
	
	static CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
		
		@Override
		public void run() {
			// Thread.currentThread().getName() +
			System.out.println("\n已经就绪\n");
		}
	});
	
	public static void main(String[] args) {
		
		for (int i = 0; i < threadNum; i++) {
			new TaskThread(barrier, i + 1).start();
			// barrier.reset();
		}
	}
	
	
}
