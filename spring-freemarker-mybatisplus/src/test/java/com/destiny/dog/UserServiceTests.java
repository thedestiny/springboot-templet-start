package com.destiny.dog;


import com.destiny.dog.entity.User;
import com.destiny.dog.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/applicationContext.xml")
public class UserServiceTests {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Test
	public void test001(){
		
		
		User user = new User();
		user.setId(2L);
		user.setUsername("");
		user.setPassword("rrrrr");
		user.setSalt("");
		
		userMapper.updateById(user);
	
	
	}
	
	
}
