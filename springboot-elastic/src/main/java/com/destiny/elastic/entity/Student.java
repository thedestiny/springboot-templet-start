package com.destiny.elastic.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

// indexStoreType  索引存储类型，一般使用niofs
@Data
@Document(indexName = "student_index",  replicas = 1,
        shards = 1, createIndex = true,indexStoreType="fs")
public class Student {



    @Id
    @Field(index = true, store = true, type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String username;

    @Field(type = FieldType.Integer)
    private Integer age;

    @Field(type = FieldType.Date)
    private Date createTime;

    @Field(type = FieldType.Text)
    private String address;



}
