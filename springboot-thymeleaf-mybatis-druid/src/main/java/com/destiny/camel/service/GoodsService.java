package com.destiny.camel.service;

import com.destiny.camel.entity.Goods;

public interface GoodsService {


    int order();
    
    /**
     * 插入数据
     * */
    Integer insertGoods(Goods entity);
    
    
    /**
     * 重试测试
     * */
    Integer retryExampleTest(String str) throws InterruptedException;
    
}
