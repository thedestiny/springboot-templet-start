package com.destiny.rabbit.entity.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserPage  extends Page {
	
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
	private Date createTime;
	private Date updateTime;
	
	
}
