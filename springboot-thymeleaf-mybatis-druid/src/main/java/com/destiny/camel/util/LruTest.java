package com.destiny.camel.util;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruTest {
	
	
	public LinkedHashMap<String, String> keyMap;
	private Integer si = 5;
	public String eldestKey;
	
	@Before
	public void init() {
		// 设置 LRU 的容量大小
		keyMap = new LinkedHashMap<String, String>(si, .75F, true) {
			private static final long serialVersionUID = 4485729692063620114L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
				boolean tooBig = size() > si;
				if (tooBig) {
					eldestKey = eldest.getKey();
					System.out.println("移除" + eldestKey);
				}
				return tooBig;
			}
		};
	}
	
	@Test
	public void test01() {
		System.out.println(keyMap.put("1", "2"));
		System.out.println(keyMap.put("2", "2"));
		System.out.println(keyMap.put("3", "2"));
		System.out.println(keyMap.put("4", "2"));
		System.out.println(keyMap.put("5", "2"));
		System.out.println(keyMap.put("6", "2"));
		System.out.println(keyMap.put("7", "2"));
		System.out.println(keyMap.put("8", "2"));
	}
	
	
	
}
