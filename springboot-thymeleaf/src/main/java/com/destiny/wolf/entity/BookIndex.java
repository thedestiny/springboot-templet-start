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
@Document(indexName = "book_index", createIndex = false)
public class BookIndex implements Serializable {
	
	private static final long serialVersionUID = -6428838993840841363L;
	
	@Id
	private String id;
	
	@Field(type = FieldType.Text, analyzer = "ik_max_word")
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Field(name = "publish_date")
	private Date publishDate;
	
	@Field(type = FieldType.Double)
	private BigDecimal price;
	
	// @Field(index = false, type = FieldType.Keyword)
	private Integer number;
	
	private String author;
	
	private List<String> tags;
	
	
}
