package com.destiny.rabbit;

import com.destiny.rabbit.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-27 5:33 PM
 */
@Component
public class UserServiceTest extends RabbitApplicationTests {

    @Autowired
    private UserService service;


    @Test
    public void test002(){

       service.updateUserInfo();

    }


}
