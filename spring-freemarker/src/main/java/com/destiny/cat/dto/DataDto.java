package com.destiny.cat.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Description
 * @Date 2023-04-21 9:24 AM
 */

@Data
public class DataDto {


    @Excel(name = "姓名")
    private String name;

    @Excel(name = "年龄")
    private String age;

}
