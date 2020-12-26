package com.destiny.camel.util;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Slf4j
public class UnsafeInstance {
	
	public static Unsafe reflectGetUnsafeObj(){
		
		try {
			Class<?> clazz = Unsafe.class;
			Field field = clazz.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(clazz);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		
		int i = DateUtil.thisDayOfWeek();
		System.out.println(i);
		
		
	}
	
	
}
