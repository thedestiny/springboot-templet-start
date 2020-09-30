package com.destiny.rabbit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.rabbit.entity.User;
import com.destiny.rabbit.entity.query.UserPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
	
	List<User> selectUserList(User user);
	
	/** 测试根据原始参数名称进行查询 */
	User selectUserListByNameAndAge(String username, Integer age);
	
	/**
	 * 分页查询数据
	 * */
	IPage<User> selectUserForPage(UserPage entity);
}
