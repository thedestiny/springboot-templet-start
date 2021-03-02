package com.destiny.shrimp.service.impl;

import com.destiny.shrimp.entity.Goods;
import com.destiny.shrimp.mapper.GoodsMapper;
import com.destiny.shrimp.service.GoodsService;
import com.destiny.shrimp.utils.CamelLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
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
