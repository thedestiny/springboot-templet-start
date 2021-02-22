package com.destiny.camel.util;

import cn.hutool.core.util.StrUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		
		
		String[] ddd = {"45", "56", "67"};
		List<String> fillMedinList = Stream.of(ddd).filter(StrUtil::isNotBlank).collect(Collectors.toList());
		System.out.println(fillMedinList);
		
	}
	
	
	@Test
	public void test001(){
		
		
		List<String> list = new ArrayList<>();
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");
		
		// 原序列发送修改后,子序列循环时会发生报错
		List<String> sub = list.subList(1,3);
		
		// list.add("11");
		
		System.out.println(list);
		System.out.println(sub);
		
		
		
		
		
	}
	
}
