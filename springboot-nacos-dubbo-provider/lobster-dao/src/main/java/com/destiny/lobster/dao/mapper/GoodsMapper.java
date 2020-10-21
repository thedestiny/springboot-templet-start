package com.destiny.lobster.dao.mapper;


import com.destiny.lobster.entity.entity.Goods;
import org.apache.ibatis.annotations.Param;

public interface GoodsMapper {


   Goods queryGoodsById(Long id);

   Integer updateGoodsStock(@Param("id") Long id, @Param("stock") Integer stock);


}
