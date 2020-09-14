package com.destiny.horse.mapper;


import com.destiny.horse.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
