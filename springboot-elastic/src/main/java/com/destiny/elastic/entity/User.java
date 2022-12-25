package com.destiny.elastic.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Date 2022-07-22 11:01 AM
 */

@Data
public class User {

    private Long id;

    private String username;

    private Integer age;

    private String address;

    private String email;

    private BigDecimal weight;

    private String birthday;

    private String createTime;

    private String updateTime;

    private Boolean sex;

    private String cellphone;

    private String company;

    private String idCard;

    private String card;

    private String university;






}
