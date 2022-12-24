package com.sk.manage.web;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

/**
 * @Description
 * @Date 2022-12-23 9:48 PM
 */

@Slf4j
@Controller
public class HomeController {


    @GetMapping(value = "home")
    public String home(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        log.info("login user id {}", userId);
        return "home";
    }

}
