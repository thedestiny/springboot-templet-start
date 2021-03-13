package com.destiny.dog.learn.example;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class GuavaCacheTest {
	
	public static void main(String[] args) {
		
		
		Cache<String,String> cache = CacheBuilder.newBuilder().build();
		       cache.put("word","Hello Guava Cache");
		       System.out.println(cache.getIfPresent("word"));
	
	}
	
}
