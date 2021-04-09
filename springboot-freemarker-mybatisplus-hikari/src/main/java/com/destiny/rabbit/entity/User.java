package com.destiny.rabbit.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "tb_user")
public class User implements Serializable {
	
	@TableId(value = "id",type = IdType.ASSIGN_ID)
	private Long id;
	private String username;
	private String salt;
	private String nickname;
	private String cellphone;
	private String password;
	private String idCard;
	
	private Date birthday;
	private Integer age;
	private BigDecimal weight;
	
	
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	
	
}
