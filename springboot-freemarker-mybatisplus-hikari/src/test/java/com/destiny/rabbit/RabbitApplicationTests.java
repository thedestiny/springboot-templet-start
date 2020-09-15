package com.destiny.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.destiny.rabbit.entity.User;
import com.destiny.rabbit.mapper.UserMapper;
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
	    
    }
    

}
