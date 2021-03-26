package com.destiny.camel.service;

import com.destiny.camel.entity.Goods;

import java.util.List;

public interface GoodsService {


    int order();
    
    /**
     * 插入数据
     * */
    Integer insertGoods(Goods entity);
    
    /**
     * 批量插入数据
     * */
    Integer insertGoodsList(List<Goods> entityList);
    
    
    /**
     * 重试测试
     * */
    Integer retryExampleTest(String str) throws InterruptedException;
    
}
