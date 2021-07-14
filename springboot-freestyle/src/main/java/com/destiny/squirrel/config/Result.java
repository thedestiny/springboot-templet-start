package com.destiny.squirrel.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-06-30 8:30 PM
 */

@Data
public class Result implements Serializable {

    private Integer code;

    private String msg;

    private Object data;


    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result() {
    }


    public static Result success(){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.msg);
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.code);
        result.setMsg(ResultCode.SUCCESS.msg);
        result.setData(data);
        return result;
    }


}
