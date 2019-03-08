package com.destiny.kitty.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /** 主界面 */
    @RequestMapping(value = {"/", "home.html","index.html"})
    public String home() {
        return "home";
    }


}
