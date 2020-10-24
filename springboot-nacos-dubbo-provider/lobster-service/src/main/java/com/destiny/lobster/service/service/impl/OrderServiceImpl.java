package com.destiny.lobster.service.service.impl;


import com.destiny.lobster.dao.GoodsMapper;
import com.destiny.lobster.entity.Goods;
import com.destiny.lobster.service.service.OrderService;

import com.destiny.lobster.utils.utils.CamelLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

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
