package com.destiny.squirrel.utils;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class InteceptorTestDemo implements MethodInterceptor {
	
	
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		
		System.out.println("before" + method.getName());
		Object invoke = methodProxy.invoke(o, objects);
		System.out.println("after");
		return invoke;
	}
	
	
}
