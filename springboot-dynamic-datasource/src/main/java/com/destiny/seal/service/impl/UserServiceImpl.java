package com.destiny.seal.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.seal.entity.User;
import com.destiny.seal.mapper.UserMapper;
import com.destiny.seal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@DS("slave")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@DS("master")
	@Override
	public Integer addUserInfo(User entity) {
		return baseMapper.insert(entity);
	}
	
	
	
}
