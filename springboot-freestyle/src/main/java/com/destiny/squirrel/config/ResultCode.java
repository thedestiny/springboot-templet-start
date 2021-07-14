package com.destiny.squirrel.config;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-30 8:33 PM
 */

public enum ResultCode {


    ERROR(1001, "失败"),
    SUCCESS(0, "成功");

    ResultCode(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public Integer code;
    public String msg;


}
