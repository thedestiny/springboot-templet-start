package com.destiny.squirrel.utils;

import net.sf.cglib.proxy.Enhancer;

import java.math.BigDecimal;
import java.sql.Connection;

public class InterceptorTest001 {
	
	public static void main(String[] args) {
		
		BigDecimal divide = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(3));
		
		System.out.println(divide);
		
		
		// Enhancer enhancer = new Enhancer();
		// enhancer.setSuperclass(InteceptorTest.class);
		// enhancer.setCallback(new InteceptorTestDemo());
		// InteceptorTest o = (InteceptorTest)enhancer.create();
		// String s = o.sayHello();
		// System.out.println(s);
		
		
		
		
		
	}
	
}
