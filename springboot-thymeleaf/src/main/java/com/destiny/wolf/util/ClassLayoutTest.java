package com.destiny.wolf.util;

import org.openjdk.jol.info.ClassLayout;

public class ClassLayoutTest {
	
	private String name;
	private static Integer level;
	private int age;
	/**
	 * 开启指针压缩/关闭指针压缩
	 * -XX:+UseCompressedOops/-XX:-UseCompressedOops
	 */
	public static void main(String[] args) {
		
		ClassLayoutTest obj = new ClassLayoutTest();
		System.out.println(ClassLayout.parseInstance(obj).toPrintable());
		synchronized (obj) {
			System.out.println(ClassLayout.parseInstance(obj).toPrintable());
		}
		
	}
	
}
