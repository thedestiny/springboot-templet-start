package com.destiny.seal.config;

import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.Data;

@Data
public class DataSourceDTO extends DataSourceProperty {
	
	/**
	 * 添加数据源名称
	 * */
	private String pollName;
}
