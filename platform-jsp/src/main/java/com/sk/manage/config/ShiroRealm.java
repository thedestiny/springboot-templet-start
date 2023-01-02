package com.sk.manage.config;

/**
 * ShiroRealm
 */

import com.alibaba.fastjson.JSONObject;
import com.sk.manage.ext.UserDto;
import com.sk.manage.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ShiroRealm extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        UserDto user = (UserDto) principalCollection.getPrimaryPrincipal();
        if (user != null) {
            //将用户的角色名称赋值给info
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            log.debug("user role is {}", user.getRole());
            info.addRole(user.getRole());
            return info;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername(); //获取用户表单中的账号
        UserDto user = userService.queryUserExtByUserName(username); //根据账号查找对应的对象
        log.info("user is {}", JSONObject.toJSONString(user));
        if (user != null) {
            if (!"1".equals(user.getStatus())) {
                throw new LockedAccountException("账号已被禁用");
            }
            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        } else {
            throw new UnknownAccountException("账号或密码错误");
        }
    }


}
