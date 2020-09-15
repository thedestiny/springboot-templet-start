package com.destiny.rabbit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.rabbit.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
	
	List<User> selectUserList(User user);
	
	/** 测试根据原始参数名称进行查询 */
	User selectUserListByNameAndAge(String username, Integer age);
}
