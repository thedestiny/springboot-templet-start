package com.destiny.shrimp.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Goods {

    private Long id;

    private String goodName;

    private Integer stock;

    private Date updateTime;

    private Date createTime;



}
