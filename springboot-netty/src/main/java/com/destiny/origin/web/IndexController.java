package com.destiny.origin.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author destiny
 * @Date 2021-11-26 4:51 PM
 */

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class IndexController {


    /**
     * resetful api 版本控制
     * 参数version=1 和 version=2
     * 接口 uri 添加 version
     * 接口 header
     *
     * @return
     */
    @GetMapping(value = "/user", params = "version=1", headers = "x-version=1")
    public String test01(){
        return "";
    }

    @GetMapping(value = "/user", params = "version=1", headers = "x-version=1")
    public String test02(){
        return "";
    }

}
