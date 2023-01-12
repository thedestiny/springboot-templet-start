package com.sk.manage.ext;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-24 12:47 AM
 */

public class PartyPageReq extends  Page{

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }
}
