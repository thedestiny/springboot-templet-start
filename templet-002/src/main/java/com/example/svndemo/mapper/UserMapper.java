package com.example.svndemo.mapper;


import com.example.svndemo.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
