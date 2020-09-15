package com.destiny.camel.mapper;


import com.destiny.camel.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
