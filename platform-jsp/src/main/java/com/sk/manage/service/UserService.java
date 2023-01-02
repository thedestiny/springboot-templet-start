package com.sk.manage.service;


import com.sk.manage.ext.UserDto;

public interface UserService {


    UserDto queryUserExtByUserName(String username);
}
