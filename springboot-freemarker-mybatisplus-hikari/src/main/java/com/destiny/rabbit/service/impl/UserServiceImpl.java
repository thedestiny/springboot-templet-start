package com.destiny.rabbit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.rabbit.entity.User;
import com.destiny.rabbit.mapper.UserMapper;
import com.destiny.rabbit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	
	@Override
	public User queryUserByNameAndAge(String username, Integer age) {
		return baseMapper.selectUserListByNameAndAge(username, age);
	}
	
	
}
