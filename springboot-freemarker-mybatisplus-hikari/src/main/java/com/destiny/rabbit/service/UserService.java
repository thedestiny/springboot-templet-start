package com.destiny.rabbit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.destiny.rabbit.entity.User;

public interface UserService extends IService<User> {
	
	User queryUserByNameAndAge(String username, Integer age);
}
