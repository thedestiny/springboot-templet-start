package com.destiny.camel;

import com.destiny.camel.entity.Goods;
import com.destiny.camel.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TempletApplicationTests {

	@Autowired
	private GoodsService goodsService;
	
	@Test
	public void test001(){
		
	
		
		// goodsService.insertGoods(entity);
		
		List<Goods> list = new ArrayList<>();
		
		for (int i = 0; i < 4 ; i++) {
			Goods entity = new Goods();
			// entity.setId(4L);
			entity.setGoodName("测试");
			entity.setStock(4);
			entity.setCreateTime(new Date());
			entity.setUpdateTime(new Date());
			list.add(entity);
		}
		
		goodsService.insertGoodsList(list);
		
	}
	
	
    @Test
    public void contextLoads() {
    }

}
