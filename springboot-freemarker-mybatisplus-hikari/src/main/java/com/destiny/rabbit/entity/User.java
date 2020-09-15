package com.destiny.rabbit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "tb_user")
public class User implements Serializable {
	
	private Integer id;
	private String username;
	private String salt;
	private String nickname;
	private String cellphone;
	private String password;
	private String idCard;
	
	private Date birthday;
	private Integer age;
	private BigDecimal weight;
	private Date createTime;
	private Date updateTime;
	
	
	
}
