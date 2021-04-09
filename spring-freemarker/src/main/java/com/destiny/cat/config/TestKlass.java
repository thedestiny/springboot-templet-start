package com.destiny.cat.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TestKlass {
	
	public static void main(String[] args) {
		
		int numLen = 23_334_399;
		
		ArrayList<String> list = new ArrayList<>();
		HashMap<String,String> map = new HashMap<>();
		map.put("rr","rr");
		
		list.add("er");
		list.add("es");
		list.add("ed");
		
		System.out.println(1<<16);
		
		for(String node : list){
			System.out.println( node);
		}
		
		for(int i =0, len = list.size(); i <len; i++){
			System.out.println(list.get(i));
		}
		
		LinkedList<String>  linkedList = new LinkedList<>();
		linkedList.add("er");
		linkedList.add("es");
		linkedList.add("ed");
		
		for(String node : linkedList){
			System.out.println( node);
		}
		
		for(int i =0, len = linkedList.size(); i <len; i++){
			System.out.println(linkedList.get(i));
		}
	}
	
	
}
