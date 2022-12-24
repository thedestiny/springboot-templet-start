package com.sk.manage.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * @Description 密码
 * @Date 2022-12-23 11:55 PM
 */
@Slf4j
public class AppCredentialsMatcher implements CredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken uToken = (UsernamePasswordToken) token;
        String pwd1 = new String(uToken.getPassword()).trim();
        String pwd2 = (String) info.getCredentials();
        log.info("db password {} and input password {}", pwd1, pwd2);
        return pwd1.equals(pwd2.trim());
    }


}
