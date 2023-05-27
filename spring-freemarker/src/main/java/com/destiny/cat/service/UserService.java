package com.destiny.cat.service;

import com.destiny.cat.entity.User;

import java.util.List;

public interface UserService {

    User findUserById(Long id);

	List<User> selectUserList();
}
