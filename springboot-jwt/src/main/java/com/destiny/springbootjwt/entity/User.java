package com.destiny.springbootjwt.entity;


import lombok.Data;


@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    private String role;


}
