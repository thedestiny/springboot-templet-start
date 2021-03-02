package com.destiny.dog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.dog.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectUserList(User user);


}
