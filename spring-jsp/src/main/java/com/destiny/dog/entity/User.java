package com.destiny.dog.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String id;

    private String name;

    private Integer age;

    private Integer height;

    public User(String name, int age, int height) {

        this.name = name;
        this.age = age;
        this.height = age;

    }
}
