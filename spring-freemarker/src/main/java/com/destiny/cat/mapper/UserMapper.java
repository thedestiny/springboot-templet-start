package com.destiny.cat.mapper;

import com.destiny.cat.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);


}
