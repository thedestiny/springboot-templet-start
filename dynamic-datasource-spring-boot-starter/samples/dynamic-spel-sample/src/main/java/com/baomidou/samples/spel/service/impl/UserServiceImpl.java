package com.baomidou.samples.spel.service.impl;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.samples.spel.entity.User;
import com.baomidou.samples.spel.mapper.UserMapper;
import com.baomidou.samples.spel.service.UserService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
@DS("slave")
public class UserServiceImpl implements UserService {

  @Resource
  private UserMapper userMapper;

  @Override
  @DS("#session.tenantName")
  public List selectSpelBySession() {
    return userMapper.selectUsers();
  }

  @Override
  @DS("#header.tenantName")
  public List selectSpelByHeader() {
    return userMapper.selectUsers();
  }

  @Override
  @DS("#tenantName")
  public List selectSpelByKey(String tenantName) {
    return userMapper.selectUsers();
  }

  @Override
  @DS("#user.tenantName")
  public List selecSpelByTenant(User user) {
    return userMapper.selectUsers();
  }
}
