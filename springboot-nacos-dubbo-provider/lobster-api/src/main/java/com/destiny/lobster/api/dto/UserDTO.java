package com.destiny.lobster.api.dto;


import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {
	
	private static final long serialVersionUID = -1234L;
	
	private String name;
	
	private Long id;
	
	
}
