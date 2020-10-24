package com.destiny.lobster.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {

    private Long id;

    private String goodName;

    private Integer stock;

    private Date updateTime;

    private Date createTime;
	
	
	public static void main(String[] args) {
		
		Goods goods = new Goods();
		goods.setCreateTime(new Date());
		System.out.println(goods);
		
	}
 
 
}
