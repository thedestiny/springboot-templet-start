package com.destiny.squirrel.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description 配置文件
 * @Author destiny
 * @Date 2021-11-09 2:55 PM
 */
@Data
@Slf4j
@SpringBootConfiguration
@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
public class AppConfig {

    /** 名称 */
    private String username;
    /** 年龄 */
    private Integer age;
   /** 体重 */
    private BigDecimal weight;
   /** 生日 */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date birthday;
    /** 爱好*/
    private List<String> hobbyList;
    /** 标签 */
    private Map<String,String> tags;


}
