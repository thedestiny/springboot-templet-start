package com.destiny.camel.util.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AtomicReferenceTest {
	
	
	public static AtomicReference<User> atomicUserRef = new AtomicReference<User>();
	
	public static void main(String[] args) {
		User user = new User("赵武灵王", 15);
		atomicUserRef.set(user);
		User updateUser = new User("火影忍者", 17);
		atomicUserRef.compareAndSet(user, updateUser);
		System.out.println(atomicUserRef.get().getName());
		System.out.println(atomicUserRef.get().getOld());
		
		
		// 初始值和版本号
		AtomicStampedReference<Integer> stamped = new AtomicStampedReference<>(100, 1);
		
		new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
				//  和乐观相似,第一个stamp相当于 version 每次加1 如果别的线程修改过就是1了.第一个线程0就对比不成功
				//
				boolean sucess = stamped.compareAndSet(100, 101, stamped.getStamp(), stamped.getStamp() + 1);
				System.out.println(sucess);
				sucess = stamped.compareAndSet(101, 100, stamped.getStamp(), stamped.getStamp() + 1);
				System.out.println(sucess);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
	}
	
	static class User {
		private String name;
		private int old;
		
		public User(String name, int old) {
			this.name = name;
			this.old = old;
		}
		
		public String getName() {
			return name;
		}
		
		public int getOld() {
			return old;
		}
	}
	
	
}

