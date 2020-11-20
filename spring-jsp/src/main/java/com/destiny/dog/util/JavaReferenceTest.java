package com.destiny.dog.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JavaReferenceTest {
	
	public static void main(String[] args) {
		
		Integer d1 = 3;
		Integer d2 = 3;
		Integer d3 = 300;
		Integer d4 = 300;
		System.out.println(d1 == d2);
		System.out.println(d3 == d4);
		
	}
	
}
