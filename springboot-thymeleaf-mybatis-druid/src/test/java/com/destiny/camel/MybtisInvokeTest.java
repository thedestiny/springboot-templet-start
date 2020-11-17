package com.destiny.camel;

import com.destiny.camel.entity.User;
import com.destiny.camel.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class MybtisInvokeTest {
	
	static SqlSessionFactory sqlSessionFactory = null;
	
	static {
		SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
		try {
			sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static SqlSession getSession() {
		return sqlSessionFactory.openSession();
	}
	
	
	public static void main(String[] args) {
		
		SqlSession session = getSession();
		UserMapper mapper = session.getMapper(UserMapper.class);
		List<User> users = mapper.queryUserList(1, BigDecimal.valueOf(2));
		users.forEach(System.out::println);
		
		// System.out.println(users);
		
		
	}
	
}
