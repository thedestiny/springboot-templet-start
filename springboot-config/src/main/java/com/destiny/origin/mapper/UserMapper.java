package com.destiny.origin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.origin.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectUserList(User user);


    List<User> queryUserList(String username, String nickname);
}
