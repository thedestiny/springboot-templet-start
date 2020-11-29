package com.destiny.dog.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaReferenceTest {
	
	public static void main(String[] args) {
		
		// -128 到 127 是在缓存中,因此 == 是相等的,超出范围则是不相等
		Integer d1 = 3;
		Integer d2 = 3;
		Integer d3 = 300;
		Integer d4 = 300;
		System.out.println(d1 == d2);
		System.out.println(d3 == d4);
		
	}
	
}
