package com.destiny.cat.config;

import lombok.Data;

/**
 * @Description 统一返回对象
 * @Date 2023-05-29 2:53 PM
 */

@Data
public class Result<T> {

    public static final Integer OK = 200;
    public static final Integer ERROR = 500;

    private Integer code;

    private String msg;

    private T data;

    public Result() {
        this.code = 200;
        this.msg = "请求成功";
    }

    public static Result error(Integer code, String msg) {
        Result res = new Result();
        res.setCode(code);
        res.setMsg(msg);
        return res;
    }

    public static Result ok() {
        return new Result();
    }


    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }


}
