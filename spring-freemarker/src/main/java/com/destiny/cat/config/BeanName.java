package com.destiny.cat.config;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * @Description
 * @Author destiny
 * @Date 2022-07-11 10:04 AM
 */
public class BeanName {

    public String url;

    public void init1() {
        this.url = "123";
    }

    public void init2() {
        this.url = "456";
    }


    public static void main(String[] args) {
        BigDecimal dd = BigDecimal.valueOf(1.000);
        boolean equals = NumberUtil.equals(BigDecimal.ONE, dd);
        System.out.println(equals);

    }

}
