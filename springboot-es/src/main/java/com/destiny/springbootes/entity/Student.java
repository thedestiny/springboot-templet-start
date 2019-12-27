package com.destiny.springbootes.entity;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Document(indexName = "demo2", type = "student", shards = 1, replicas = 1, refreshInterval = "1s")
public class Student implements Serializable {

    private static final long serialVersionUID = -6428838993840841365L;

    private Long id;

    private String hobby;

    private Date createTime;

    private Integer bike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBike() {
        return bike;
    }

    public void setBike(Integer bike) {
        this.bike = bike;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", hobby='" + hobby + '\'' +
                ", createTime=" + createTime +
                ", bike=" + bike +
                '}';
    }
}
