package com.destiny.origin;

import com.alibaba.fastjson.JSONObject;
import com.destiny.origin.entity.User;
import com.destiny.origin.mapper.UserMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-04-20 2:31 PM
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testSave(){
        log.info("testSave");

        Faker faker = new Faker(Locale.CHINESE);

        User user = new User();
        user.setUsername(faker.name().username());
        user.setSalt(faker.number().digit());
        user.setNickname(faker.name().name());
        user.setIdCard(faker.idNumber().invalidSvSeSsn());
        user.setAge(faker.number().randomDigit());
        user.setPassword(faker.medical().diseaseName());
        user.setCellphone(faker.phoneNumber().cellPhone());
        user.setTest(faker.address().buildingNumber());

        log.info("user is {}", JSONObject.toJSONString(user));

        userMapper.insert(user);


    }


}
