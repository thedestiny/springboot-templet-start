package com.destiny.dog.learn.agent;

public class MapperProxyTest {
	
	public static void main(String[] args) {
		
		
		MapperProxy proxy = new MapperProxy();
		
		UserTestMapper mapper = proxy.newInstance(UserTestMapper.class);
		UserTest user = mapper.getUserById(1001);
		int hashCode = mapper.hashCode();
		System.out.println(hashCode);
		
		System.out.println("ID:" + user.getId());
		System.out.println("Name:" + user.getName());
		System.out.println("Age:" + user.getAge());
		
		System.out.println(mapper.toString());
		
		
	}
	
	
}
