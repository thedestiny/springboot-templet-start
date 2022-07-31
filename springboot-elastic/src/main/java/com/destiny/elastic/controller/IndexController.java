package com.destiny.elastic.controller;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.destiny.elastic.entity.User;
import com.github.javafaker.Faker;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2022-07-22 11:08 AM
 */

@RestController
public class IndexController {


    @Autowired
    private RestHighLevelClient client;


    @GetMapping(value = "index")
    public String createIndex() throws IOException {

        //1、构建 创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("xk_index");//索引名
        //2、客户端执行请求,获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        //3、打印
        System.out.println("创建成功，创建的索引名为：" + response.index());

        return "success";

    }

    public static Faker faker;
    public static Snowflake snow;


    static {

        faker = new Faker(Locale.SIMPLIFIED_CHINESE);
        snow = new Snowflake(1, 1);

    }


    public void bulkOpt() {

        BulkRequest bulk = new BulkRequest();

        for (int i = 0; i < 100; i++) {

            User user = genUser();
            IndexRequest index = new IndexRequest();
            index.id(snow.nextIdStr()).source(JSONObject.toJSONString(user),XContentType.JSON);
            bulk.add(index);
        }
    }

    public static User genUser(){

        User user = new User();
        user.setId(snow.nextId());
        user.setUsername(faker.name().username());
        user.setAge(faker.random().nextInt(10,50));
        user.setAddress(faker.address().fullAddress());
        user.setEmail(faker.internet().emailAddress());
        double v = faker.number().randomDouble(2, 20, 60);
        user.setWeight(BigDecimal.valueOf(v));
        user.setBirthday(faker.date().birthday(20, 50));
        user.setCreateTime(faker.date().past(300, TimeUnit.DAYS));
        user.setSex(faker.random().nextBoolean());
        user.setCellphone(faker.phoneNumber().phoneNumber());
        user.setCompany(faker.company().name());
        user.setCard(faker.finance().creditCard());
        user.setIdCard(faker.idNumber().ssnValid());
        return user;
    }


    public static void main(String[] args) {

        User user = genUser();
        String s = JSONObject.toJSONString(user, SerializerFeature.PrettyFormat);

        System.out.println(s);


    }

}
