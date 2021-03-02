package com.destiny.lobster.entity.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GoodsDTO implements Serializable {

    private Long id;

    private String goodName;

    private Integer stock;

    private Date updateTime;

    private Date createTime;



}
