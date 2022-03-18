package com.destiny.origin.web;

import com.alibaba.fastjson.JSONObject;
import com.destiny.origin.config.AppConfig;
import com.destiny.origin.event.AppOriginEvent;
import com.destiny.origin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.Future;

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


    @Autowired
    private UserService userService;

    // http://localhost:9890/api/config
    @GetMapping(value = {"config", "config1"})
    @ResponseBody
    public String config() {
        log.info(" config {}", JSONObject.toJSONString(config));
        return JSONObject.toJSONString(config);
    }

    // http://localhost:9890/api/config?constant=123
    // http://localhost:9890/api/config?constant=xxx
    // @GetMapping(value = "config", params = "constant=123")
    @GetMapping(value = "config", params = "constant")
    @ResponseBody
    public String config1() {
        log.info(" config {}", JSONObject.toJSONString(config));
        return JSONObject.toJSONString(config);
    }
    /**
     * 查询传入请求头参数
     *
     * @param appid
     * @param age
     * @return
     */
    @GetMapping(value = "index")
    public String index(@RequestHeader String appid, String age) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("token");
        System.out.println(age);
        System.out.println(appid);
        System.out.println(token);

        log.info(" config {}", JSONObject.toJSONString(config));
        return JSONObject.toJSONString(config);
    }

    @Autowired
    private ApplicationEventPublisher publisher;

    // localhost:9890/api/pub/task
    @GetMapping(value = "pub/task")
    public String publishTask() {
        log.info("main thread id {} ", Thread.currentThread().getId());
        publisher.publishEvent(new AppOriginEvent("data", "msg"));
        return "finish";
    }

    /**
     * 参数接收
     *
     * @param token
     * @param name
     */
    @GetMapping(value = "/head/param/{file:.+}")
    public String headerParameter(@RequestHeader(value = "token") String token,
                                  String name,
                                  @PathVariable("file") String file) {
        log.info("header token {} and name {} file {}", token, name, file);
        return "";
    }

    /**
     * 请求头 resetful
     *
     * @param headsMap 请求头
     * @param name     传入参数
     * @param username 名称信息
     * @param userId   id 信息
     * @return
     */
    @GetMapping(value = "/head/param1/{username:[a-zA-Z0-9_]+}/{userId}")
    public String headerParameter1(@RequestHeader Map<String, String> headsMap,
                                   @RequestParam String name,
                                   @PathVariable String username,
                                   @PathVariable Long userId) {
        log.info("header token {} and name {} username {} userId {}", headsMap, name, username, userId);
        return "";
    }

    /**
     * 异步任务
     *
     * @return
     */
    @GetMapping(value = "async/task")
    public String asyncTask() {

        Future<String> future = userService.asyncTask();
        return "";
    }

}
