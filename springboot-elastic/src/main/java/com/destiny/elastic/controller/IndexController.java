package com.destiny.elastic.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.destiny.elastic.entity.User;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Description
 * @Date 2022-07-22 11:08 AM
 */

@Slf4j
@RestController
public class IndexController {


    @Autowired
    private RestHighLevelClient client;

    @Resource
    private ElasticsearchRestTemplate template;


    @GetMapping(value = "user/page")
    public  List<User> queryUser(){

        Integer pageNum = 1;
        Integer pageSize = 20;

        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("username", "李");// .analyzer("ik_smart");//分词查询(这里指定分词只是分传过来的参数)[它是要和es索引里的分词后数据一一对应才能返回]
                                                                    /*例子：前台传了 (山东省:粗粒度分为山东省 细粒度分为：山东省,山东,省)
                                                                        es索引库里(山东省济南市 粗粒度分为 山东省,济南市  细粒度分为:山东省,山东,省,济南市,济南,市)
                                                                        只有当前台分的词和后台分的词能有一个匹配上就可以*/
        builder.withQuery(matchQueryBuilder);
        builder.withPageable(PageRequest.of(pageNum == null || pageNum == 0 ? 0 : pageNum - 1, pageSize));
        //4.构建查询对象
        NativeSearchQuery query = builder.build();

        IndexCoordinates index = IndexCoordinates.of("user_index");
        AggregatedPage<User> page = template.queryForPage(query, User.class, index);
        long total = page.getTotalElements(); // 总记录数
        int pages = page.getTotalPages();  // 总页数
        int current = page.getPageable().getPageNumber(); // 当前页号

        List<User> beanList = page.toList();  // 当前页数据集
        // Set<User> beanSet = page.toSet();  // 当前页数据集
        List<User> content = page.getContent();
        log.info(" total {} pages {} current {}", total, pages, current);
        for (User user : content) {
            log.info(" user is {}", JSONObject.toJSONString(user));

        }
        return content;


    }



    @GetMapping(value = "user/save")
    public String saveUsers() {

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("user_index");

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = genUser();
            users.add(user);
        }
        // 索引保存数据
        IndexCoordinates coordinates = IndexCoordinates.of("user_index");
        Iterable<User> save = template.save(users, coordinates);
        // 通过主键删除数据
        String delete = template.delete("123456789", coordinates);

        // 更新数据
        UpdateQuery.Builder update = UpdateQuery
                .builder("123456")
                .withDocument(Document.from(BeanUtil.beanToMap(genUser())));
        template.update(update.build(), coordinates);
        // 查询数据
        User t = template.get("123456", User.class, coordinates);

        return "success";

//        BulkRequest bulkRequest = new BulkRequest("user_index");
//
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            User user = genUser();
//            users.add(user);
//            IndexRequest index = new IndexRequest().source(XContentType.JSON,
//                    user);
//            bulkRequest.add(index);
//        }
//        client.bulk(bulkRequest, RequestOptions.DEFAULT);
//
//        template.bulkIndex(bulkRequest);


//        List<IndexQuery> indexQueries = users.stream().map(product -> {
//            IndexQuery query = new IndexQueryBuilder()
//                    .withIndexName("product")
//                    .withType("product")
//                    .withId(product.getId() + "")
//                    .withSource(JSON.toJSONString(product)).build();
//            return query;
//        }).collect(Collectors.toList());


    }


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

    @PostMapping(value = "save/data")
    public String saveData() throws IOException {

        String idxName = "indexName";
        Integer batchSize = 3000;
        List<Map<String, Object>> skuList = new ArrayList<>();

        BulkRequest bulkRequest = buildSkuBulkRequest("indexName", batchSize, skuList);
        client.bulk(bulkRequest, RequestOptions.DEFAULT);

        return "";
    }

    private BulkRequest buildSkuBulkRequest(String indexName, int batchSize,
                                            List<Map<String, Object>> skuList) {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        Random random = new Random();
        for (int j = 0; j < batchSize; j++) {
            int index = random.nextInt(100_000); // 从这 10 万商品数据随机选
            // 参数封装：略。
            IndexRequest indexRequest = new IndexRequest().source(XContentType.JSON,
                    skuList);
            bulkRequest.add(indexRequest);
        }
        return bulkRequest;
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
            index.id(snow.nextIdStr()).source(JSONObject.toJSONString(user), XContentType.JSON);
            bulk.add(index);
        }
    }

    public static User genUser() {

        User user = new User();
        user.setId(snow.nextId());
        user.setUsername(faker.name().username());
        user.setAge(faker.random().nextInt(10, 50));
        user.setAddress(faker.address().fullAddress());
        user.setEmail(faker.internet().emailAddress());
        double v = faker.number().randomDouble(2, 20, 60);
        user.setWeight(BigDecimal.valueOf(v));
        Date birthday = faker.date().birthday(20, 50);
        Date past = faker.date().past(300, TimeUnit.DAYS);

        user.setBirthday(DateUtil.format(birthday, "yyyy-MM-dd HH:mm:ss"));
        user.setCreateTime(DateUtil.format(past, "yyyy-MM-dd HH:mm:ss"));
        user.setUpdateTime(DateUtil.format(past, "yyyy-MM-dd HH:mm:ss"));
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
