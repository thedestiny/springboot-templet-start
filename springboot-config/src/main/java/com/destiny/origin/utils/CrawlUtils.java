package com.destiny.origin.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description
 * @Author destiny
 * @Date 2022-04-27 2:48 PM
 */

@Slf4j
public class CrawlUtils {


    public static void main(String[] args) {

        String s = HttpUtil.get("https://api.inews.qq.com/newsqa/v1/query/inner/publish/modules/list?modules=statisGradeCityDetail,diseaseh5Shelf");
        JSONObject jsonObject = JSONObject.parseObject(s);
        JSONObject data = jsonObject.getJSONObject("data");

        JSONArray jsonArray = data.getJSONObject("diseaseh5Shelf").getJSONArray("areaTree")
                .getJSONObject(0).getJSONArray("children");

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject node = jsonArray.getJSONObject(i);
            String name = node.getString("name");
            JSONObject total = node.getJSONObject("total");
            Integer dead = total.getIntValue("dead");
            Integer heal = total.getIntValue("heal");
            Integer confirm = total.getIntValue("confirm");

            log.info("name {} head {} heal {} confirm {}", name, dead, heal, confirm);

        }


    }
}
