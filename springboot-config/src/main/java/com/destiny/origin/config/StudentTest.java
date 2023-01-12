package com.destiny.origin.config;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author destiny
 * @Date 2022-02-21 12:40 PM
 */
public class StudentTest {


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Student student = new Student();
        student.setName("小明");

        Class<? extends Student> klass = student.getClass();
        // 获取类的方法 获取本类以及父类或者父接口中的所有公共方法
        Method[] methods = klass.getMethods();
        // 获取本类中的所有方法 包括 private protect 默认的或者 public 的
        Method[] declaredMethods = klass.getDeclaredMethods();

        Method say = klass.getDeclaredMethod("say", String.class);
        say.setAccessible(true);
        Object sam = say.invoke(student, "sam");

        System.out.println("---------");
        Field[] declaredFields = klass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            field.setAccessible(true);
            System.out.println("字段名称" + field.getName());
        }


    }
}
