package com.destiny.hiootamus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.hiootamus.entity.primary.User;

public interface UserService extends IService<User> {
	
	
	Integer addUserInfo(User entity);
	
}
