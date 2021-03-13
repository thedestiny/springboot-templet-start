package com.destiny.dog.learn.juc;

import java.util.concurrent.locks.StampedLock;

public class GeoPoint {
	
	
	//一个点的x，y坐标
	private double x, y;
	
	/**
	 * Stamped类似一个时间戳的作用，每次写的时候对其+1来改变被操作对象的Stamped值
	 * 这样其它线程读的时候发现目标对象的Stamped改变，则执行重读
	 */
	private final StampedLock sl = new StampedLock();
	
	//【写锁(排它锁)】
	public void move(double deltaX, double deltaY) {// an exclusively locked method
		/**stampedLock调用writeLock和unlockWrite时候都会导致stampedLock的stamp值的变化
		 * 即每次+1，直到加到最大值，然后从0重新开始*/
		long stamp = sl.writeLock(); //写锁
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);//释放写锁
		}
	}
	
	//【乐观读锁】
	public double distanceFromOrigin(double lableX,double lableY) { // A read-only method
		/**
		 * tryOptimisticRead是一个乐观的读，使用这种锁的读不阻塞写
		 * 每次读的时候得到一个当前的stamp值（类似时间戳的作用）
		 */
		long stamp = sl.tryOptimisticRead();
		//这里就是读操作，读取x和y，因为读取x时，y可能被写了新的值，所以下面需要判断
		double currentX = x, currentY = y;
		/**如果读取的时候发生了写，则stampedLock的stamp属性值会变化，此时需要重读，
		 * validate()：比较当前stamp和获取乐观锁得到的stamp比较，不一致则失败。
		 * 再重读的时候需要加读锁（并且重读时使用的应当是悲观的读锁，即阻塞写的读锁）
		 * 当然重读的时候还可以使用tryOptimisticRead，此时需要结合循环了，即类似CAS方式
		 * 读锁又重新返回一个stampe值*/
		if (!sl.validate(stamp)) {//如果验证失败（读之前已发生写）
			stamp = sl.readLock(); //悲观读锁
			try {
				currentX = x;
				currentY = y;
			} finally {
				sl.unlockRead(stamp);//释放读锁
			}
		}
		//读锁验证成功后执行计算，即读的时候没有发生写
		
		
		return Math.sqrt((lableX - currentX) * (lableX - currentX) + (lableY - currentY) * (lableY - currentY));
	}
	
	//读锁升级为写锁
	public void moveIfAtOrigin(double newX, double newY) { // upgrade
		// 读锁（这里可用乐观锁替代）
		long stamp = sl.readLock();
		try {
			//循环，检查当前状态是否符合
			while (x == 0.0 && y == 0.0) {
				long ws = sl.tryConvertToWriteLock(stamp);
				//如果写锁成功
				if (ws != 0L) {
					stamp = ws;// 替换stamp为写锁戳
					x = newX;//修改数据
					y = newY;
					break;
				}
				//转换为写锁失败
				else {
					//释放读锁
					sl.unlockRead(stamp);
					//获取写锁（必要情况下阻塞一直到获取写锁成功）
					stamp = sl.writeLock();
				}
			}
		} finally {
			//释放锁（可能是读/写锁）
			sl.unlock(stamp);
		}
	}
	
	public static void main(String[] args) {
		
		GeoPoint geoPoint = new GeoPoint();
		double v = geoPoint.distanceFromOrigin(4,6);
		System.out.println(v);
		
		
	}
	
	
	
}
