package com.destiny.squirrel.utils;

import com.destiny.squirrel.entity.User;
import lombok.extern.slf4j.Slf4j;
import ognl.Ognl;
import ognl.OgnlException;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description ognl 数据
 * @Author destiny
 * @Date 2021-11-18 10:10 AM
 */
@Slf4j
public class OgnlTest {


    public static void main(String[] args) throws OgnlException {

        User user = new User();
        user.setName("小明");
        user.setId(2L);
        user.setAddress("北京市");
        user.setEmail("xieyue86@163.com");

        Map<String, String> context = new HashMap<>();
        context.put("introduction", "My name is ");
        // 测试从Root对象中进行表达式计算并获取结果
        Object name = Ognl.getValue(Ognl.parseExpression("name"), user);
        System.out.println(name.toString());


        // 测试从上下文环境中进行表达式计算并获取结果
        Object contextValue = Ognl.getValue(Ognl.parseExpression("#introduction"), context, user);
        System.out.println(contextValue);
        // 测试同时从将Root对象和上下文环境作为表达式的一部分进行计算
        Object hello = Ognl.getValue(Ognl.parseExpression("#introduction + name"), context, user);
        System.out.println(hello);

        // 对Root对象进行写值操作
        // Ognl.setValue("group.name", user, "dev");
        Ognl.setValue("age", user, "18");

        System.out.println(user);




    }

}
