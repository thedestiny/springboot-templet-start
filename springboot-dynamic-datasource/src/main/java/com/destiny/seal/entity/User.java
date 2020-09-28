package com.destiny.seal.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "tb_user")
public class User implements Serializable {
	
	@TableId(type= IdType.ASSIGN_ID, value = "id")
	private Long id;
	
	@TableField(value = "username")
	private String username;
	
	@TableField(value = "salt")
	private String salt;
	
	@TableField(value = "nickname")
	private String nickname;
	
	@TableField(value = "id_card")
	private String idCard;
	
	@TableField(value = "age")
	private Integer age;
	
	@TableField(value = "password")
	private transient String password;
	
	@TableField(value = "cellphone")
	private String cellphone;
	
	@TableField(exist = false) // 不存在的字段
	private String test;
	
}
