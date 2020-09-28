package com.destiny.seal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.seal.entity.User;

public interface UserService extends IService<User> {
	
	
	Integer addUserInfo(User entity);
	
}
