package com.destiny.elastic.entity;

import cn.easyes.annotation.IndexField;
import cn.easyes.common.enums.FieldType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Date 2023-01-06 1:56 PM
 */

@Data
public class EsProductAttributeValue implements Serializable {


    private static final long serialVersionUID = 4667965126423275401L;

    @IndexField(fieldType = FieldType.LONG)
    private Long id;

    @IndexField(fieldType = FieldType.KEYWORD)
    private Long productAttributeId;

    //属性值
    @IndexField(fieldType = FieldType.KEYWORD)
    private String value;

    //属性参数：0->规格；1->参数
    @IndexField(fieldType = FieldType.INTEGER)
    private Integer type;

    //属性名称
    @IndexField(fieldType=FieldType.KEYWORD)
    private String name;


}
