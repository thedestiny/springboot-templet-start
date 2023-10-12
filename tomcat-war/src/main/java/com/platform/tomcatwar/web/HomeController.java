package com.platform.tomcatwar.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "home")
public class HomeController {



    @GetMapping(value = "/index")
    public String index(){
        return "index";
    }


}
