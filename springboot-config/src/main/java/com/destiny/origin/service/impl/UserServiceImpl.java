package com.destiny.origin.service.impl;

import com.destiny.origin.entity.User;
import com.destiny.origin.mapper.UserMapper;
import com.destiny.origin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @ConditionalOnProperty
 * @Description
 * @Author destiny
 * @Date 2022-01-21 2:20 PM
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    // 配置事务管理器 传播属性 事务超时时间 隔离级别 异常回滚 异常不回滚
    @Transactional(
            transactionManager = "platformTransactionManager",
            propagation = Propagation.REQUIRED,
            timeout = 20,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = RuntimeException.class,
            noRollbackFor = IllegalAccessException.class
    )
    @Override
    public Integer saveUserInfo(User entity) {
        return userMapper.insert(entity);
    }

    @Transactional
    @Override
    public Integer updateUserInfo(User entity) {
        //todo some business
        // 使用AopContext 获取当前的代理对象,然后进行执行方法即可实现
        UserService userService = (UserService) AopContext.currentProxy();
        userService.modifyUserInfo();
        return null;
    }

    @Transactional
    @Override
    public Integer modifyUserInfo() {
        return null;
    }

    @Async
    @Override
    public Future<String> asyncTask() {
        log.info("asyncTask execute!");
        return CompletableFuture.completedFuture("async-task");
    }
}
