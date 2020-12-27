package com.destiny.camel.config;

import com.destiny.camel.base.BaseEntity;

import java.io.Serializable;

public class GoodsTest extends BaseEntity implements Serializable {
	
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public static void main(String[] args) {
		
		GoodsTest goodsTest = new GoodsTest();
		goodsTest.setId(34L);
		goodsTest.setName("45555");
		System.out.println(goodsTest.toString());
		
	}
	
	
}
