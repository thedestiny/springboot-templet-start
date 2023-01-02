package com.sk.manage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

	private static final long serialVersionUID = 5868240102271916592L;

	// 主键
	private Integer id;
    // 用户名
	private String username;
   // 密码
	private String password;

	private String status;

	private String role;

	private String cellphone;

	private Date createTime;

	private Date updateTime;




}
