package com.destiny.dog.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private transient Integer height;

    private List<String> tags;

    public User(String name, int age, int height) {

        this.name = name;
        this.age = age;
        this.height = age;

    }

    public User() {
    }
	
	
	public static void main(String[] args) {
	
         User user = new User();
         user.setHeight(34);
         user.setAge(34);
		 System.out.println(JSONObject.toJSONString(user));
   
 
 
	}
 
}
