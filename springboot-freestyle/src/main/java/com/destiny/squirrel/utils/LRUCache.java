package com.destiny.squirrel.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache {
	
	private LinkedHashMap<Integer, Integer> hashMap;
	
	LRUCache(int capacity) { // xieyue86@163.com
		
		this.hashMap = new LinkedHashMap<Integer, Integer>(capacity, .75F, true) {
			private static final long serialVersionUID = 4485729692063620114L;
			@Override
			protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
				boolean tooBig = size() > capacity;
				if (tooBig) {
					// eldestKey = eldest.getKey();
				}
				return tooBig;
			}
		};
	}
	
	public int get(int key) {
		return hashMap.get(key) == null ? -1 : hashMap.get(key);
		
	}
	
	public void put(int key, int value) {
		hashMap.put(key, value);
	}
	
	public static void main(String[] args) {
		
		LRUCache lRUCache = new LRUCache(2);
		lRUCache.put(1, 1); // 缓存是 {1=1}
		lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
		lRUCache.get(1);    // 返回 1
		lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
		lRUCache.get(2);    // 返回 -1 (未找到)
		lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
		lRUCache.get(1);    // 返回 -1 (未找到)
		lRUCache.get(3);    // 返回 3
		lRUCache.get(4);    // 返回 4
	
	
	}

	
}
