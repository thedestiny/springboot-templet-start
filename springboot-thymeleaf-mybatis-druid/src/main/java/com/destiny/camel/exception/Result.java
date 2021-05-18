package com.destiny.camel.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-18 10:57 AM
 */
public final class Result {

    private Result() {
    }

    public static String error(CodeMsg codeMsg) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", codeMsg.code);
        jsonObject.put("msg", codeMsg.msg);

        return jsonObject.toString();
    }


}
