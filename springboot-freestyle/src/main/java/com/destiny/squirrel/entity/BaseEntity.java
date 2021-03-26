package com.destiny.squirrel.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 7926101484561397317L;
	
	private Long ids = 0L;
	
	
}
