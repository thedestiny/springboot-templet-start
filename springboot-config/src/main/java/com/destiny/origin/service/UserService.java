package com.destiny.origin.service;

import java.util.concurrent.Future;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-01-21 2:20 PM
 */
public interface UserService {


    public Integer saveUserInfo();


    public Integer updateUserInfo();


    public Integer modifyUserInfo();

    Future<String> asyncTask();
}
