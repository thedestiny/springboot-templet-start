package com.destiny.rabbit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.destiny.rabbit.entity.User;
import com.destiny.rabbit.mapper.UserMapper;
import com.destiny.rabbit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	/**
	 * spring 事务失效场景 判断是否 aop 失效
	 * 1 bean 没有被spring 管理
	 * 2 方法不是 public 的
	 * 3 方法内调用  无事务 -> 有事务方法 或者 tx -> tx_new
	 * 4 无事务管理器 没有开启 @EnableTransactionManagement
	 * 5 数据库不支持事务
	 * 6 异常被 catch
	 * 7 异常捕获 rollbackFor 不适合
	 * 8 propagation Propagation.REQUIRED
	 * 9 事务和业务代码不在一个线程中
	 *
	 * */
	
	@Override
	public User queryUserByNameAndAge(String username, Integer age) {
		return baseMapper.selectUserListByNameAndAge(username, age);
	}


	@Transactional
	@Override
	public void updateUserInfo() {

		User user = new User();
		user.setId(3L);
		user.setNickname("fvddd");

		log.info("execute !");


		// int num = 1/0;
		// this.test();
		//updateUserInfo2();
        new Thread(new Runnable() {
			@Override
			public void run() {
				baseMapper.updateById(user);

				int num = 1/0;
			}
		}).start();

		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void test(){

	}


	@Transactional
	@Override
	public void updateUserInfo2() {

		User user = new User();
		user.setId(2L);
		user.setNickname("45");

		log.info("execute !");

		baseMapper.updateById(user);

		int num = 1/0;



	}

}
