package com.destiny.camel.service;

import com.destiny.camel.entity.Goods;

public interface GoodsService {


    int order();
    
    /**
     * 插入数据
     * */
    Integer insertGoods(Goods entity);
}
