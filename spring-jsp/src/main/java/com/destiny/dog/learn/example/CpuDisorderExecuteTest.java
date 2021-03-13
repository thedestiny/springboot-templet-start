package com.destiny.dog.learn.example;

public class CpuDisorderExecuteTest {
	
	private static int a = 0, b = 0;
	private static int x = 0, y = 0;
	
	public static void main(String[] args) throws InterruptedException {
		
		int i = 0;
		for (; ; ) {
			i++;
			a = 0;
			b = 0;
			x = 0;
			y = 0;
			
			Thread t1 = new Thread(() -> {
				a = 1;  // 11
				x = b;  // 12
			});
			Thread t2 = new Thread(() -> {
				b = 1; // 21
				y = a; // 22
			});
			t1.start();
			t2.start();
			
			t1.join();
			t2.join();
			
			// 当 12 和 22 在所在线程中与 11 和 21 交换顺序,则证明是乱序执行
			if (x == 0 && y == 0) {
				System.out.println("第 " + i + " 次执行 finish!");
				break;
			}
			
			
		}
		
		
	}
	
	
}
