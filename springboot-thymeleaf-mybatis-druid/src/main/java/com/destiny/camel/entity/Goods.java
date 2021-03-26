package com.destiny.camel.entity;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

@Data
public class Goods implements Serializable {
	
	private Long id;
	
	private String goodName;
	
	private Integer stock;
	
	private Date updateTime;
	
	private Date createTime;
	
	
}
