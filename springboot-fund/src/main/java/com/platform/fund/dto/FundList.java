package com.platform.fund.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 列表数据
 * @Description
 * @Date 2023-01-10 8:53 AM
 */

@Data
public class FundList implements Serializable {

    private static final long serialVersionUID = -8529899091511246937L;

    private List<String> managerList;

    private List<String> companyList;

    private List<String> fundType;


}
