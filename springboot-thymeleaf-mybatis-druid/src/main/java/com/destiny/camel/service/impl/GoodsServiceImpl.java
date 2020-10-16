package com.destiny.camel.service.impl;

import com.destiny.camel.entity.Goods;
import com.destiny.camel.mapper.GoodsMapper;
import com.destiny.camel.service.GoodsService;
import com.destiny.camel.util.CamelLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 同步信息
     * */
    private static CamelLock sync = new CamelLock();


    @Override
    public int order() {

        sync.lock();
        Goods goods = goodsMapper.queryGoodsById(1L);
        if (goods != null && goods.getStock() > 0) {
            goodsMapper.updateGoodsStock(1L,1);
        }
        sync.unlock();

        return 0;
    }
}
