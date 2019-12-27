package com.destiny.springbootes.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "demo1", type = "user", shards = 1, replicas = 1, refreshInterval = "1s")
public class User implements Serializable {

    private static final long serialVersionUID = -6428838993840841363L;

    private Long id;
    private String name;
    private String address;
    private Double weight;
    private Integer age;
    private Date birthday;
    private String home;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", birthday=" + birthday +
                ", home='" + home + '\'' +
                '}';
    }
}
