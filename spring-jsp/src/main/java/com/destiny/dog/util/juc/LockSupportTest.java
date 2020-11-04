package com.destiny.dog.util.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
	
	public static void main(String[] args) throws InterruptedException {
		
		
		Thread thread = new Thread(() ->{
			
			for (int i = 0; i < 10; i++) {
				if( i == 5){
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
		
		
		
	}
	
	
}
