package com.destiny.seal.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.seal.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectUserList(User user);
}
