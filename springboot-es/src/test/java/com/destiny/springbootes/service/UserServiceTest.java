package com.destiny.springbootes.service;

import com.destiny.springbootes.SpringbootEsApplicationTests;
import com.destiny.springbootes.entity.User;
import com.destiny.springbootes.utils.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

@Component
public class UserServiceTest extends SpringbootEsApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void testAdd() {


        for (int i = 0; i < 1000; i++) {
            User entity = new User();
            entity.setId((long) i);
            entity.setAddress(StringUtils.getRandomChar() + "");
            entity.setAge(new Random().nextInt(60));
            entity.setBirthday(new Date());
            entity.setHome("UK" + i);
            entity.setWeight(new Random().nextDouble());
            entity.setName("xssss" + i);
            userService.add(entity);
        }
    }

    @Test
    public void test02(){

        userService.queryUserList();
    }



}