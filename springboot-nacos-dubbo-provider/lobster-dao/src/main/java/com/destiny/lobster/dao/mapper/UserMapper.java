package com.destiny.lobster.dao.mapper;



import com.destiny.lobster.entity.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> selectUserList(User user);
}
