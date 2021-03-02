package com.destiny.dog.web;

import com.destiny.dog.entity.User;
import com.destiny.dog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(IndexController.class);

    /**
     * 主界面
     */
    @RequestMapping(value = {"/", "home.html", "index.html"})
    public String home(HttpServletRequest request) {

        String uri = request.getRequestURI();
        User user = userService.findUserById(1L);
        logger.info(" access uri is {} and user is {}", uri, user);

        return "home";
    }


}
