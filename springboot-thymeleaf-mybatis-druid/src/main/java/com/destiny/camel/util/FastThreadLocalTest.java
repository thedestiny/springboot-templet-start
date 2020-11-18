package com.destiny.camel.util;

import io.netty.util.concurrent.FastThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.StringJoiner;

@Slf4j
public class FastThreadLocalTest {
	
	
	public static void main(String[] args) {
		
		
		FastThreadLocal<String> fastThreadLocal = new FastThreadLocal<String>() {
			
			@Override
			protected String initialValue() throws Exception {
				return "23";
			}
		};
		
		fastThreadLocal.set("34");
		
		HashMap<String, String> map = new HashMap<>();
		map.get("33");
		
		StringJoiner stringJoiner = new StringJoiner(",", "[", "]");
		stringJoiner.setEmptyValue("void");
		stringJoiner.add("34");
		stringJoiner.add("45");
		stringJoiner.add(null);
		stringJoiner.add("");
		
		StringJoiner stringJoiner1 = new StringJoiner(",").add("hello").add("guys").add("欢迎关注公众号Java技术栈");
		System.out.println(stringJoiner1.toString());
		
		System.out.println(stringJoiner.toString());
		
	}
	
	
}
