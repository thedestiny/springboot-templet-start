package com.destiny.dog.util.juc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

@Slf4j

public class ExchangerTest {
	

	/**
	 * Exchanger 用于两个线程交换数据
	 *
	 * */
	@Test
	public void test001() throws InterruptedException {
		
		
		Exchanger<Integer> exchanger = new Exchanger<Integer>();
		new Consumer("consumer ", exchanger).start();
		new Producer("producer ", exchanger).start();
		TimeUnit.SECONDS.sleep(7);
		// System.exit(-1);
	
	
	
	
	}
	
	
	
	static class Producer extends Thread {
		private Exchanger<Integer> exchanger;
		private static int data = 0;
		Producer(String name, Exchanger<Integer> exchanger) {
			super("Producer-" + name);
			this.exchanger = exchanger;
		}
		
		@Override
		public void run() {
			for (int i=1; i<2; i++) {
				try {
					TimeUnit.SECONDS.sleep(1);
					data = i;
					System.out.println(getName()+" 交换前:" + data);
					data = exchanger.exchange(data);
					System.out.println(getName()+" 交换后:" + data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class Consumer extends Thread {
		private Exchanger<Integer> exchanger;
		private static int data = 0;
		Consumer(String name, Exchanger<Integer> exchanger) {
			super("Consumer-" + name);
			this.exchanger = exchanger;
		}
		
		@Override
		public void run() {
			while (true) {
				data = 0;
				System.out.println(getName()+" 交换前:" + data);
				try {
					TimeUnit.SECONDS.sleep(1);
					data = exchanger.exchange(data);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(getName()+" 交换后:" + data);
			}
		}
	}
}
