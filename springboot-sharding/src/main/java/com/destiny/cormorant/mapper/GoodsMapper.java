package com.destiny.cormorant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.destiny.cormorant.model.Goods;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author liang
 * @since 2021-08-07
 */
public interface GoodsMapper extends BaseMapper<Goods> {


   /**
     * 批量插入
     * */
   Integer insertEntityList(List<Goods> entityList);

  /**
     * 新增
     * */
   Integer insertEntity(Goods entity);

  /**
     * 根据Id更新
     * */
   Integer updateEntityById(Goods entity);

    Integer updateSelectiveById(Goods entity);
}
