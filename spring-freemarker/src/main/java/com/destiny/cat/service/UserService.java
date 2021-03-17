package com.destiny.cat.service;

import com.destiny.cat.entity.User;

import java.util.List;

public interface UserService {

    public User findUserById(Long id);
	
	List<User> selectUserList();
}
