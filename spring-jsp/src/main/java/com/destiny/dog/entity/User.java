package com.destiny.dog.entity;

import com.alibaba.fastjson.JSONObject;
import com.destiny.dog.basic.BaseEntity;
import com.destiny.dog.learn.ServiceUtils;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.LockSupport;

@Data
public class User extends BaseEntity implements Serializable {
	
	private String name;
	
	private Integer age;
	
	private transient Integer height;
	
	private List<String> tags;
	
	public User(String name, int age, int height) {
		
		this.name = name;
		this.age = age;
		this.height = age;
		
	}
	
	public User() {
	}
	
	
	public static void main(String[] args) {
		
		User user = new User();
		user.setHeight(34);
		user.setAge(34);
		System.out.println(JSONObject.toJSONString(user));
		
		System.out.println(System.currentTimeMillis());
		LockSupport.parkNanos(10 * 1000000);
		System.out.println(System.currentTimeMillis());
		
		
		Map<Class<?>, String> map = new HashMap<>();
		
		Class<?> ee = user.getClass();
		map.put(user.getClass(), "1");
		String s = map.get(ee);
		System.out.println(s);
		System.out.println("eee");
		
		
		User user1 = new User();
		user1.setHeight(32);
		user1.setAge(3);
		
		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user1);
		userList.add(user1);
		userList.add(user1);
		userList.add(user1);
		Map<Integer, List<User>> integerListMap = ServiceUtils.convertToListMap(Lists.newArrayList(32, 44, 56, 67, 89), userList, User::getHeight);
		integerListMap.forEach((k, v) -> {
			System.out.println(v);
		});
		
		ConcurrentHashMap<String, String> chm = new ConcurrentHashMap<>();
		chm.put("34", "56");
		
		
		Map<Integer, List<User>> integerListMap1 = ServiceUtils.convertToListMap(userList, User::getAge);
		System.out.println(integerListMap1);
		
		Class<?> klazz = user.getClass();
		
		/**
		 getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
		 getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
		 
		 ···········································································································
		 getDeclaredMethod：获取当前类的所有声明的方法，包括public、protected和private修饰的方法。需要注意的是，这些方法一定是在当前类中声明的，从父类中继承的不算，实现接口的方法由于有声明所以包括在内。
		 getMethod：获取当前类和父类的所有public的方法。这里的父类，指的是继承层次中的所有父类。比如说，A继承B，B继承C，那么B和C都属于A的父类。
		 ···········································································································
		 getDeclaredConstructors()的返回所有类型的构造器，包括public和非public
		 getConstructors()只返回public。
		 * */
		Field[] fields = klazz.getFields();
		Field[] declaredFields = klazz.getDeclaredFields();
		
	}
	
}
