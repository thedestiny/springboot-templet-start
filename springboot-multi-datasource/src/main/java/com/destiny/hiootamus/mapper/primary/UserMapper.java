package com.destiny.hiootamus.mapper.primary;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.hiootamus.entity.primary.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectUserList(User user);
}
