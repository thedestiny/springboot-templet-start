package com.destiny.origin.service;

import com.destiny.origin.entity.User;

import java.util.concurrent.Future;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-01-21 2:20 PM
 */
public interface UserService {


    public Integer saveUserInfo(User entity);


    public Integer updateUserInfo(User entity);


    public Integer modifyUserInfo();

    Future<String> asyncTask();
}
