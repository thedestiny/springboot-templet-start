package com.destiny.origin.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Date 2022-04-07 11:34 AM
 */

@Data
public class User {

    private String name;

    private Integer age;

    private List<String> tags;

    private BigDecimal weight;

    public User(String name, Integer age, List<String> tags, BigDecimal weight) {
        this.name = name;
        this.age = age;
        this.tags = tags;
        this.weight = weight;
    }

    public User() {
    }
}
