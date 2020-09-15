package com.destiny.eagle.mapper;


import com.destiny.eagle.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
