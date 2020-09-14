package com.destiny.horse.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
	
	private Integer id;
	private String username;
	private String salt;
	private String nickname;
	private String cellphone;
	private String idcard;
	
	
}
