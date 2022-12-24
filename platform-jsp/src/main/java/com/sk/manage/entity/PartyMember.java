package com.sk.manage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Date 2022-12-24 12:31 AM
 */
@Data
public class PartyMember implements Serializable {


    /**
     * 主键
     */
    private Integer id;

    /**
     * 党员名称
     */
    private String username;

    /**
     * 状态 1 正常 0 禁用
     */
    private String status;

    /**
     * 党员地址
     */
    private String address;

    /**
     * 电话号码
     */
    private String cellphone;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 介绍人
     */
    private String introducer;


    private Date createTime;

    private Date updateTime;



}
