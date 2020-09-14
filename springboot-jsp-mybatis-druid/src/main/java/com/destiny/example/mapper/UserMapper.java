package com.destiny.example.mapper;


import com.destiny.example.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
