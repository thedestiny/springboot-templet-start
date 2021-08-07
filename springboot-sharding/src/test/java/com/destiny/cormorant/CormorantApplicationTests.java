package com.destiny.cormorant;

import java.math.BigDecimal;
import java.util.Locale;


import com.destiny.cormorant.mapper.GoodsMapper;
import com.destiny.cormorant.model.Goods;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class CormorantApplicationTests {

    @Test
    public void contextLoads() {
    }


    @Autowired
    private GoodsMapper goodsMapper;


    @Test
    public void test001() {

        Faker faker = new Faker(Locale.CHINA);
        for (int i = 0; i < 10; i++) {
            Goods goods = new Goods();
            goods.setGoodsName(faker.food().dish());
            goods.setPrice(BigDecimal.valueOf(faker.number().randomDouble(2,100,200)));
            goods.setCreateTime(faker.date().birthday());
            goods.setImage(faker.animal().name());
            goods.setSupplier(faker.name().fullName());
            goodsMapper.insert(goods);
        }

    }


}
