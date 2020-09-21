package com.destiny.wolf.util;

import java.util.concurrent.ConcurrentHashMap;

public class CHMTestDemo {
	
	public static void main(String[] args) {
		
		
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<>();
		
		Integer a1 = hashMap.computeIfAbsent("a", (key) -> 1 + 1);
		Integer a2 = hashMap.computeIfPresent("a", (key, value) -> hashMap.get(key) + value);
		System.out.println(a1);
		System.out.println(a2);
		hashMap.compute("b",(key, value) -> hashMap.get(key) + value);
		
		// 不存在时插入
		hashMap.putIfAbsent("c",5);
		
		
	}
	
	
}
