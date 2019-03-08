package com.destiny.kitty.mapper;

import com.destiny.kitty.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);


}
