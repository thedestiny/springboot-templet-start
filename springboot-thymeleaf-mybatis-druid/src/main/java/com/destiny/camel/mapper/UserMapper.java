package com.destiny.camel.mapper;


import com.destiny.camel.entity.User;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UserMapper {
	
	List<User> selectUserList(User user);
	
	// List<User> queryUserList(@Param("age") Integer age, @Param("weight") BigDecimal weight);
	List<User> queryUserList(Integer age, BigDecimal weight);
	
	List<User> queryUserList1(Integer age, BigDecimal weight);
}
