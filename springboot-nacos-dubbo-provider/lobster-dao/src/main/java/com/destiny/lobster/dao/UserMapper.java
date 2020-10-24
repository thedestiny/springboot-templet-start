package com.destiny.lobster.dao;



import com.destiny.lobster.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
