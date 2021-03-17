package com.destiny.squirrel.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaJavaTest {
	
	@Test
	public void test001(){
		
		Function<String,Integer> function = node -> Integer.valueOf(node);
		System.out.println(function.apply("45"));
		Consumer<String> consumer = (node) -> System.out.println("consumer -> " + node);
		consumer.andThen(consumer).accept("45");
		
		List<String> list = new ArrayList<>();
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		
		Spliterator<String> spliterator = list.spliterator();
		// 计数器
		int cnt = 0;
		// concurrency parallel print element
		while (spliterator.tryAdvance(System.out::println)) {
			System.out.println("cnt = " + ++cnt);
		}
		list.parallelStream().forEach(System.out::println);
		String computeStr = list.parallelStream().collect(Collectors.joining("-"));
		System.out.println(computeStr);
		
		Map<String,String> map = new HashMap<>(3,1.3f);
		map.put("dd","45");
		
		ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<>(23,4.0f,3);
		chm.put("34","rt");
		
	}
	
	
}
