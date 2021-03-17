package com.destiny.cat.config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TestKlass {
	
	public static void main(String[] args) {
		
		
		ArrayList<String> list = new ArrayList<>();
		list.add("er");
		list.add("es");
		list.add("ed");
		
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
