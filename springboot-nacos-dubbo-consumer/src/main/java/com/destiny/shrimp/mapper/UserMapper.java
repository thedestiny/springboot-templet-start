package com.destiny.shrimp.mapper;


import com.destiny.shrimp.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
