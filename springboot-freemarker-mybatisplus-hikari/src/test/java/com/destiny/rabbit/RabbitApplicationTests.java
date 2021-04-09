package com.destiny.rabbit;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.destiny.rabbit.entity.User;
import com.destiny.rabbit.entity.query.UserPage;
import com.destiny.rabbit.mapper.UserMapper;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitApplicationTests {

    @Test
    public void contextLoads() {
    }
    
    
    @Autowired
    private UserMapper userMapper;
    
    
    @Test
    public void test001(){
	
	    User xiaoming = userMapper.selectUserListByNameAndAge("xiaoming", 2);
	    log.info("user is {}", JSONObject.toJSONString(xiaoming));
	
	    User user = userMapper.selectById(4);
	    log.info("user is {}",user);
	
	
	    IPage<User> userPage = userMapper.selectUserForPage(new UserPage());
	    // log.info("user page {}",JSONObject.toJSONString(userPage, SerializerFeature.PrettyFormat));
	
	    Faker faker = new Faker(Locale.CHINA);
	    
	    User user1 = new User();
	    user1.setUsername(faker.name().fullName());
	    user1.setSalt(faker.code().imei());
	    user1.setNickname(faker.name().username());
	    user1.setCellphone(faker.phoneNumber().cellPhone());
	    user1.setPassword(faker.code().gtin8());
	    user1.setIdCard(faker.idNumber().ssnValid());
	    user1.setBirthday(new Date());
	    user1.setAge(3);
	    user1.setWeight(new BigDecimal("0.34"));
	    
	    userMapper.insert(user1);
	
    }
    

}
