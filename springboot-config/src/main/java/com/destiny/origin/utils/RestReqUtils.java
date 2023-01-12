package com.destiny.origin.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 * @Date 2023-01-12 11:05 AM
 */

@Slf4j
public class RestReqUtils {

    public static void main(String[] args) {


        RestTemplate template = new RestTemplate();

        String url = "https://eq.10jqka.com.cn/open/api/etf_tab/hq_tab/v1/list/all/trackindex";

        ResponseEntity<String> entity = template.getForEntity(url, String.class);

        String body = entity.getBody();
        JSONObject json = JSONObject.parseObject(body);
        System.out.println("\n====\n");
        JSONArray nodeList = json.getJSONObject("data").getJSONArray("list");


        for (int i = 0; i < nodeList.size(); i++) {
            JSONObject jsonObject = nodeList.getJSONObject(i);
            System.out.println(jsonObject);

        }




    }


}
