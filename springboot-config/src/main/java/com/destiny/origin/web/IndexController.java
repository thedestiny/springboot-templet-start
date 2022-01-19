package com.destiny.origin.web;

import com.alibaba.fastjson.JSONObject;
import com.destiny.origin.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
