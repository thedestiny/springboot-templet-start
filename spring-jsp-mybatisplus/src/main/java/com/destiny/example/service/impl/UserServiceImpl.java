package com.destiny.example.service.impl;

import com.destiny.example.entity.User;
import com.destiny.example.mapper.UserMapper;
import com.destiny.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Long id) {
        User user = new User();
        user.setId(id);
        List<User> users = userMapper.selectUserList(user);
        if(users != null && !users.isEmpty()){
            return users.get(0);
        }
        return new User();
    }
}