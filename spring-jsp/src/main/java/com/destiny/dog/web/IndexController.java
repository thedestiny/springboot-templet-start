package com.destiny.dog.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@Controller
public class IndexController {

    /** 主界面 */
    @RequestMapping(value = {"/", "home.html","index.html"})
    public String home() {
        return "home";
    }


}
