package com.sk.manage.mapper;

import com.sk.manage.entity.User;

public interface UserMapper {

    User selectUserByName(String username);
}
