package com.destiny.camel.mapper;


import com.destiny.camel.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsMapper {


   Goods queryGoodsById(Long id);

   Integer updateGoodsStock(@Param("id") Long id, @Param("stock") Integer stock);
	
	/**
	 * 插入数据
	 * */
	Integer insert(Goods entity);
	
	/**
	 * 批量插入
	 * */
	Integer insertEntityList(List<Goods> entityList);
}
