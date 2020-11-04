package com.destiny.camel.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.destiny.camel.entity.Goods;
import com.destiny.camel.mapper.GoodsMapper;
import com.destiny.camel.service.GoodsService;
import com.destiny.camel.util.CamelLock;
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
	 */
	CamelLock sync = new CamelLock();
	
	@Override
	public int order() {
		
		try {
			sync.lock();
			Goods goods = goodsMapper.queryGoodsById(1L);
			log.info(" goods is -> ", JSONObject.toJSONString(goods));
			if (goods != null && goods.getStock() > 0) {
				goodsMapper.updateGoodsStock(1L, 1);
			}
			
		}catch (Exception e){
			log.info("e is ",e);
		} finally {
			sync.unlock();
		}
		
		
		
		return 0;
	}
}
