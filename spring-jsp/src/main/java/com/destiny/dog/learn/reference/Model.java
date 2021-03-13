package com.destiny.dog.learn.reference;

public class Model {
	
	private byte[] rr = new byte[4 * 1024 * 1024];
	
	@Override
	protected void finalize() throws Throwable {
		// super.finalize();
		System.out.println("对象被回收");
	}
}
