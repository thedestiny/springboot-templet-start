package com.sk.manage.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Date 2022-12-24 12:30 AM
 */

@Data
public class Organization implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 组织名称
     */
    private String username;

    /**
     * 组织状态 1 正常 0 禁用
     */
    private String status;

    /**
     * 组织地址
     */
    private String address;

    /**
     * 电话号码
     */
    private String cellphone;

    /**
     * 联系人
     */
    private String liaison;

    private Date createTime;

    private Date updateTime;
}
