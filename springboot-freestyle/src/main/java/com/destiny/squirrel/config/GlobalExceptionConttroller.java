package com.destiny.squirrel.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 全局异常捕获
 * @Author liangwenchao
 * @Date 2021-11-19 3:29 PM
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConttroller {


    /**
     * 最大的异常捕获类，用于最后兜底的异常的处理器
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        JSONObject json = new JSONObject();
        json.put("message", e.getMessage());
        json.put("code", 101);
        json.put("data", "Some Data");
        json.put("url", req.getRequestURL().toString());
        log.error(" error information {}", json);
        return json.toString();
    }

    /**
     * 用于捕获自己自定的异常，自定义的异常是处理器可以有多个，
     * 发生异常的时候如果匹配到的自定义的异常则不再匹配上面的最大的异常处理器（即：jsonErrorHandler）
     *
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public String myjsonErrorHandler(HttpServletRequest req, RuntimeException e) throws Exception {
        JSONObject json = new JSONObject();
        json.put("code", 101);
        json.put("message", e.getMessage());
        json.put("data", "Some Data");
        json.put("url", req.getRequestURL().toString());
        log.error(" error information {}", json);
        return json.toString();
    }


}
