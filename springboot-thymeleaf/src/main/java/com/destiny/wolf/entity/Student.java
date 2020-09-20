package com.destiny.wolf.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "student", shards = 1, replicas = 1, refreshInterval = "1s")
public class Student implements Serializable {

    private static final long serialVersionUID = -6428838993840841365L;

    private Long id;

    private String hobby;

    private Date createTime;

    private Integer bike;
    
    
    
    
}
