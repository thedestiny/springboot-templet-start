package com.platform.fund.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2023-01-09 5:20 PM
 */

@Data
public class Result<T> implements Serializable {


    private String code;

    private String msg;

    private T data;





}
