package com.destiny.camel.base;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;


public abstract class BaseEntity implements Serializable {
	
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		//  ToStringBuilder
		String reflection = ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		System.out.println(reflection);
		return reflection;
	}
	
}
