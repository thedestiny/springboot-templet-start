package com.destiny.origin.utils;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description
 * @Date 2022-04-07 11:34 AM
 */

@Data
public class Student {

    private String name;

    private Integer age;

    private List<String> tags;

    private BigDecimal weight;

    public Student(String name, Integer age, List<String> tags, BigDecimal weight) {
        this.name = name;
        this.age = age;
        this.tags = tags;
        this.weight = weight;
    }

    public Student() {
    }
}
