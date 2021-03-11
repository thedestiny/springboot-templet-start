package com.destiny.squirrel.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

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
		
		Spliterator<String> spliterator = list.spliterator();
		// 计数器
		int cnt = 0;
		// concurrency parallel print element
		while (spliterator.tryAdvance(System.out::println)) {
			System.out.println("cnt = " + ++cnt);
		}
		list.parallelStream().forEach(System.out::println);
		
		
	}
	
	
}
