package com.destiny.dog.entity;

import com.alibaba.fastjson.JSONObject;
import com.destiny.dog.util.ServiceUtils;
import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.LockSupport;

@Data
public class User implements Serializable {
	
	private String id;
	
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
		Map<Integer, List<User>> integerListMap = ServiceUtils.convertToListMap(Lists.newArrayList(32,44,56,67,89), userList, User::getHeight);
		integerListMap.forEach((k,v) -> {
			System.out.println(v);
		});
		
	}
	
}
