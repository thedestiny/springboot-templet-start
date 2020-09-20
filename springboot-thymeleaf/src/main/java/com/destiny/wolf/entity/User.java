package com.destiny.wolf.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "user",  shards = 1, replicas = 1, refreshInterval = "1s")
public class User implements Serializable {

    private static final long serialVersionUID = -6428838993840841363L;

    private Long id;
    private String name;
    private String address;
    private Double weight;
    private Integer age;
    private Date birthday;
    private String home;
    
    
}
