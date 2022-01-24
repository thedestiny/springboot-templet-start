package com.destiny.origin.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Data
@Component
@ConfigurationProperties(prefix = "app.info")
public class AppConfig {

    private String name;

    private Integer age;

    private Long height;

    private Double money;

    // @DateTimeFormat 注解是为了解析配置文件中的日期格式，否则会报转换异常的错误
    // @JSONField 注解是为了在打印日志的时候使用json序列化成年月日的格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")
    private Date birthday;

    private BigDecimal weight;
    // 解析 list 和 map 的数据结构,以及下划线配置转为驼峰
    private List<String> userPets;
    private Map<String,String> userExt;


}
