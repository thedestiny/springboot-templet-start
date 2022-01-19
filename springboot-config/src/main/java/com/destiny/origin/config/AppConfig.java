package com.destiny.origin.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Component
@ConfigurationProperties(prefix="app.info")
public class AppConfig {

   private String name;

   private Integer age;

   private Long height;

   private Double money;

   @DateTimeFormat(pattern="yyyy-MM-dd")
   @JSONField(format="yyyy-MM-dd")
   private Date birthday;

   private BigDecimal weight;


}
