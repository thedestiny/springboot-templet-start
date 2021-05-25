package com.destiny.hiootamus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.hiootamus.entity.primary.User;
import com.destiny.hiootamus.mapper.primary.UserMapper;
import com.destiny.hiootamus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	
	@Override
	public Integer addUserInfo(User entity) {
		return baseMapper.insert(entity);
	}
	
	
	
}
