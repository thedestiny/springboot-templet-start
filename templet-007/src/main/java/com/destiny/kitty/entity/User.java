package com.destiny.kitty.entity;

import com.destiny.kitty.basic.BaseEntity;

import java.io.Serializable;

public class User extends BaseEntity implements Serializable  {

    private static final long serialVersionUID = -7505678942156357962L;
    private Long id ;
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
