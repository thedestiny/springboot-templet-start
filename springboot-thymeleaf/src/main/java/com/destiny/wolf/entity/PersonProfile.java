package com.destiny.wolf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Document(indexName = "person_profile", createIndex = true)
public class PersonProfile implements Serializable {
	
	private static final long serialVersionUID = -6428838993840841365L;
	
	@Id
	private Long id;
	
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 地址
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String address;
	
	/**
	 * 邮编
	 */
	@Field(name = "postal_code")
	private String postalCode;
	
	
	/**
	 * 身份证
	 */
	@Field(name = "id_card")
	private String idCard;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 公司
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String company;
	
	/**
	 * 工作
	 */
	@Field(type = FieldType.Keyword)
	private String job;
	
	/**
	 * 电话
	 */
	private String phone;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	
	/**
	 * 简介
	 */
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String sentence;
	
	/**
	 * 薪水
	 */
	@Field(type = FieldType.Double)
	private BigDecimal salary;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Field(name = "create time")
	private Date createTime;
	
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String paragraph;
	
	
	private List<String> words;
	
	
}
