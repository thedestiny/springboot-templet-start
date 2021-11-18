package com.destiny.hiootamus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.hiootamus.entity.primary.User;
import com.destiny.hiootamus.mapper.primary.UserMapper;
import com.destiny.hiootamus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public Integer addUserInfo(User entity) {
		return baseMapper.insert(entity);
	}
	


	public void insertList01(){
		SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);//跟上述sql区别
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			User user = new User();
			user.setId((long)i);
			user.setUsername("name" + i);
			user.setPassword("password" + i);
			userMapper.insert(user);
		}
		sqlSession.commit();
		long end = System.currentTimeMillis();
		System.out.println("---------------" + (start - end) + "---------------");

	}



	public void insertList02(){
		// ，使用PreparedStatement加批量的方法

//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection(o_url, userName, password);
//			conn.setAutoCommit(false);
//			String sql = "INSERT into tbl_person (name,age) VALUES(?,?)";
//			PreparedStatement prest = conn.prepareStatement(sql);
//			for(int x = 0; x < size; x++){
//				prest.setString(1, "张三");
//				prest.setString(2, 20);
//				prest.addBatch();
//			}
//			prest.executeBatch();
//			conn.commit();
//			conn.close();
//		} catch (SQLException ex) {
//			Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
//		} catch (ClassNotFoundException ex) {
//			Logger.getLogger(MyLogger.class.getName()).log(Level.SEVERE, null, ex);
//		}
//
//		conn.setAutoCommit(false);
//		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//		for(int x = 0; x < size; x++){
//			stmt.addBatch("INSERT INTO adlogs(ip,website,yyyymmdd,hour,object_id) VALUES('192.168.1.3', 'localhost','20081009',8,'23123')");
//		}
//		stmt.executeBatch();
//		conn.commit();
//
//		conn.setAutoCommit(false);
//		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//				ResultSet.CONCUR_READ_ONLY);
//		for(int x = 0; x < size; x++){
//			stmt.execute("INSERT INTO adlogs(ip,website,yyyymmdd,hour,object_id) VALUES('192.168.1.3', 'localhost','20081009',8,'23123')");
//		}
//		conn.commit();
	}
	
}
