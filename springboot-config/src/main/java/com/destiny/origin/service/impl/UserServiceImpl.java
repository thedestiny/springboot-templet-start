package com.destiny.origin.service.impl;

import com.destiny.origin.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-01-21 2:20 PM
 */
@Service
public class UserServiceImpl implements UserService {

    // 配置事务管理器 传播属性 事务超时时间 隔离级别 异常回滚 异常不回滚
    @Transactional(
            transactionManager = "platformTransactionManager",
            propagation= Propagation.REQUIRED,
            timeout = 20,
            isolation = Isolation.READ_COMMITTED,
            rollbackFor = RuntimeException.class,
            noRollbackFor = IllegalAccessException.class
    )
    @Override
    public Integer saveUserInfo() {
        return null;
    }

    @Transactional
    @Override
    public Integer updateUserInfo() {
        //todo some business
        // 使用AopContext 获取当前的代理对象,然后进行执行方法即可实现
        UserService userService = (UserService)AopContext.currentProxy();
        userService.modifyUserInfo();
        return null;
    }

    @Transactional
    @Override
    public Integer modifyUserInfo() {
        return null;
    }

}