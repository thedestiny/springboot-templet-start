package com.platform.fund.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Date 2023-01-09 5:16 PM
 */

@Data
public class EtfPageReq extends Page<EtfPageReq> implements Serializable {


    private String alias;

    private BigDecimal fundSize;

    private String fundManager;

    private String fundCompany;

    private BigDecimal stageWeek1;

    private BigDecimal stageMonth1;




}
