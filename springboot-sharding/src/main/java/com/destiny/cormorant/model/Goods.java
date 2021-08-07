package com.destiny.cormorant.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品表
 *
 * @author liang
 * @since 2021-08-07
 */
@Data
@TableName("t_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId // (type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主图
     */
    private String image;

    /**
     * 供应商
     */
    private String supplier;


}

