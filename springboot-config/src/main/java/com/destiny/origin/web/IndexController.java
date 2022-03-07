package com.destiny.origin.web;

import com.alibaba.fastjson.JSONObject;
import com.destiny.origin.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-11-26 4:51 PM
 */

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class IndexController {

    @Autowired
    private AppConfig config;

    @GetMapping(value = "config")
    @ResponseBody
    public String config(){
        log.info(" config {}", JSONObject.toJSONString(config));
        return JSONObject.toJSONString(config);
    }

    /**
     * 查询传入请求头参数
     * @param appid
     * @param age
     * @return
     */
    @GetMapping(value = "index")
    public String index(@RequestHeader String appid, String age ){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        System.out.println(age);
        System.out.println(appid);
        System.out.println(token);

        log.info(" config {}", JSONObject.toJSONString(config));
        return JSONObject.toJSONString(config);
    }
}
