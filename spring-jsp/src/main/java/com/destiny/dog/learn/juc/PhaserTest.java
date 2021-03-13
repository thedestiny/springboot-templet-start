package com.destiny.dog.learn.juc;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {
	
	/**
	 * 分阶段并发
	 * 做3道题“，每个学生一个进程，5个学生可以并行做，这个就是常规的并发，但是如果加一个额外的 限制条件，
	 * 必须等所有人都做完类第一题，才能开始做第二题，必须等所有人都做完了第二题，才能做第三题，
	 * 这个问题就转变成了分阶段并发的问题
	 */
	
	public static void main(String[] args) {
		
		ExamPhaser phaser = new ExamPhaser();
		
		StudentTask[] studentTask = new StudentTask[5];
		
		for (int i = 0; i < studentTask.length; i++) {
			studentTask[i] = new StudentTask(phaser);
			phaser.register();  //注册一次表示 phaser 维护的线程个数
		}
		
		Thread[] threads = new Thread[studentTask.length];
		for (int i = 0; i < studentTask.length; i++) {
			threads[i] = new Thread(studentTask[i], "Student " + i);
			threads[i].start();
		}
		
		//等待所有线程执行结束
		for (int i = 0; i < studentTask.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("考试结束 :" + phaser.isTerminated());
		
		
	}
	
	
	static class StudentTask implements Runnable {
		private Phaser phaser;
		
		public StudentTask(Phaser phaser) {
			this.phaser = phaser;
		}
		
		@Override
		public void run() {
			System.out.println(Thread.currentThread().getName() + "到达考试");
			phaser.arriveAndAwaitAdvance();
			
			System.out.println(Thread.currentThread().getName() + "做第1题时间...");
			doExercise1();
			System.out.println(Thread.currentThread().getName() + "做第1题完成...");
			phaser.arriveAndAwaitAdvance();
			
			System.out.println(Thread.currentThread().getName() + "做第2题时间...");
			doExercise2();
			System.out.println(Thread.currentThread().getName() + "做第2题完成...");
			phaser.arriveAndAwaitAdvance();
			
			System.out.println(Thread.currentThread().getName() + "做第3题时间...");
			doExercise3();
			System.out.println(Thread.currentThread().getName() + "做第3题完成...");
			phaser.arriveAndAwaitAdvance();
		}
		
		private void doExercise1() {
			long duration = (long) (Math.random() * 10);
			try {
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void doExercise2() {
			long duration = (long) (Math.random() * 10);
			try {
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		private void doExercise3() {
			long duration = (long) (Math.random() * 10);
			try {
				TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	static class ExamPhaser extends Phaser {
		
		@Override
		protected boolean onAdvance(int phase, int registeredParties) { //在每个阶段执行完成后回调的方法
			
			switch (phase) {
				case 0:
					return studentArrived();
				case 1:
					return finishFirstExercise();
				case 2:
					return finishSecondExercise();
				case 3:
					return finishExam();
				default:
					return true;
			}
			
		}
		
		private boolean studentArrived() {
			System.out.println("学生准备好了,学生人数：" + getRegisteredParties());
			return false;
		}
		
		private boolean finishFirstExercise() {
			System.out.println("第一题所有学生做完");
			return false;
		}
		
		private boolean finishSecondExercise() {
			System.out.println("第二题所有学生做完");
			return false;
		}
		
		private boolean finishExam() {
			System.out.println("第三题所有学生做完，结束考试");
			return true;
		}
		
	}
	
	
}
