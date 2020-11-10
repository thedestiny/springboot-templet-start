package com.destiny.camel.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.destiny.camel.entity.Goods;
import com.destiny.camel.exception.CamelException;
import com.destiny.camel.mapper.GoodsMapper;
import com.destiny.camel.service.GoodsService;
import com.destiny.camel.util.CamelLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

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
			
		} catch (Exception e) {
			log.info("e is ", e);
		} finally {
			sync.unlock();
		}
		
		
		return 0;
	}
	
	
	@Override
	@Transactional
	public Integer insertGoods(Goods entity) {
		
		int num = goodsMapper.insert(entity);
		int nn = 1 / 0;
		
		return num;
	}
	
	// 针对 CamelException 进行重试，重试2次， 每次间隔时间是上次间隔时间的2倍,达到最大重试次数时调用recover方法
	@Override
	@Retryable(value = CamelException.class,
			recover = "recover",
			maxAttempts = 2, backoff = @Backoff(delay = 500, multiplier = 2))
	public Integer retryExampleTest(String str) throws InterruptedException {
		
		TimeUnit.SECONDS.sleep(1);
		if (RandomUtil.randomInt(3, 4) < 3.9) {
			throw new CamelException("333");
		}
		return 0;
	}
	
	@Recover
	public void recover(CamelException e, String str1) {
		log.info("recover e is {}", e);
		log.info("recover e parameter is  {}", str1);
		// ... error handling making use of original args if required
	}
}
