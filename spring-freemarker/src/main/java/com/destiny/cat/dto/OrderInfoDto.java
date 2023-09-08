package com.destiny.cat.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Date 2023-09-07 8:26 PM
 */
@Data
public class OrderInfoDto implements Serializable {

    private static final long serialVersionUID = -306584714990267992L;

    // 用户id
    private String userId;
    // 交易金额
    private BigDecimal amount;
    // 订单号
    private String orderNo;

    public OrderInfoDto() {
    }

    public OrderInfoDto(String userId, BigDecimal amount, String orderNo) {
        this.userId = userId;
        this.amount = amount;
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
