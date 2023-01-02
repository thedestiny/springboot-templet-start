package com.sk.manage.service.impl;

import com.sk.manage.entity.User;
import com.sk.manage.ext.UserDto;
import com.sk.manage.mapper.UserMapper;
import com.sk.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto queryUserExtByUserName(String username) {
        User user = userMapper.selectUserByName(username);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
