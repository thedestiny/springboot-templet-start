package com.destiny.shrimp.web;

import com.destiny.lobster.api.LobsterServiceApi;
import com.destiny.lobster.api.dto.UserDTO;
import com.destiny.shrimp.service.GoodsService;
import com.google.gson.JsonObject;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private GoodsService goodsService;

    @DubboReference(version = "1.0")
    private LobsterServiceApi lobsterServiceApi;



    @RequestMapping(value = {"/","home.html","index.html"})
    public String home(HttpServletRequest request){

        // sync.lock();
        String url = request.getRequestURI();
        String date = new Date().toString();
        logger.info(" home is {} and date {}", url,date );
        // sync.unlock();
        return "home";
    }

    @GetMapping(value = "order")
    @ResponseBody
    public String order(){

    	// http://localhost:9090/order
	    List<UserDTO> userDTOS = lobsterServiceApi.queryUserList(new UserDTO());
	    userDTOS.forEach(System.out::println);
	    // int num = goodsService.order();
        JsonObject jsonObject = new JsonObject();
        return jsonObject.toString();
    }


}
