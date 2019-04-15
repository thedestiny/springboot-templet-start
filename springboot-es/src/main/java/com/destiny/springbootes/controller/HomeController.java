package com.destiny.springbootes.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {


    @RequestMapping(value = "/cart/goods/speciesnumber/query")
    @ResponseBody
    public String test(@RequestBody GoodsQuery goodsQuery) {

        JSONObject json = new JSONObject(true);

        json.put("status", "success");
        JSONObject jso = new JSONObject(true);
        Map<Long, Integer> map = new HashMap<Long, Integer>();
        for (Long me : goodsQuery.getMerchantIds()) {
            jso.put(me + "", 23);
            map.put(me, 65);
        }
        json.put("result", map);
        return json.toString();
    }
}
