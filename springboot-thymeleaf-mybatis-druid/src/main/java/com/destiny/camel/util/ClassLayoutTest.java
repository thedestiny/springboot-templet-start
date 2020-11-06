package com.destiny.camel.util;

import org.openjdk.jol.info.ClassLayout;

public class ClassLayoutTest {
	
	
	private String name;
	private int age;
	
	/**
	 * 开启指针压缩/关闭指针压缩
	 * -XX:+UseCompressedOops/-XX:-UseCompressedOops
	 */
	public static void main(String[] args) {
		
		
		Object obj1 = new Object();
		ClassLayoutTest obj = new ClassLayoutTest();
		System.out.println(ClassLayout.parseInstance(obj1).toPrintable());
		// synchronized (obj) {
		// 	System.out.println(ClassLayout.parseInstance(obj).toPrintable());
		// }
	}
	
}
