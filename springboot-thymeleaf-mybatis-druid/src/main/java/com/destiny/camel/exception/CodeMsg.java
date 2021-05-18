package com.destiny.camel.exception;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-18 10:54 AM
 */
public enum  CodeMsg {

    ACCESS_LIMIT_REACHED("102","超出访问次数限制");

    CodeMsg(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code,msg;


}
