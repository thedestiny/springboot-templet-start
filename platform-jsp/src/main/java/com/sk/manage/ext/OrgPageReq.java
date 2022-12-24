package com.sk.manage.ext;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-12-24 12:47 AM
 */

public class OrgPageReq  extends  Page{

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

    public String getLiaison() {
        return liaison;
    }

    public void setLiaison(String liaison) {
        this.liaison = liaison;
    }
}
