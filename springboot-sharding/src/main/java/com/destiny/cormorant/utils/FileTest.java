package com.destiny.cormorant.utils;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-08-17 2:42 PM
 */
public class FileTest {


    public static void main(String[] args) {

        String s = FileUtil.readString("/Users/admin/Desktop/draft_content.json", "UTF-8");

        JSONObject parse = JSONObject.parseObject(s);
        // System.out.println(parse);
        JSONObject materials = parse.getJSONObject("materials");
        JSONArray texts = materials.getJSONArray("texts");

        for (int i = 0; i < texts.size(); i++) {
            JSONObject jsonObject = texts.getJSONObject(i);
            String cnt = jsonObject.getString("content").split("><size=")[1];
            cnt = cnt.replace("</size></color></font>", "");
            cnt = cnt.split(">")[1];
            System.out.println(cnt);

        }

        // System.out.println(parse.toString(SerializerFeature.PrettyFormat));


    }
}
