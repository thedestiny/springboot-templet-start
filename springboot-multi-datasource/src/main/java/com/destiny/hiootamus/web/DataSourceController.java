package com.destiny.hiootamus.web;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.JndiDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/datasource")
public class DataSourceController {
	
	@Autowired
	private DynamicRoutingDataSource ds;
	@Autowired
	private DataSourceCreator dataSourceCreator;
	@Autowired
	private BasicDataSourceCreator basicDataSourceCreator;
	@Autowired
	private JndiDataSourceCreator jndiDataSourceCreator;
	@Autowired
	private DruidDataSourceCreator druidDataSourceCreator;
	@Autowired
	private HikariDataSourceCreator hikariDataSourceCreator;
	
	@GetMapping(value = "list")
	public Set<String> now() {
		return ds.getCurrentDataSources().keySet();
	}
	
	@DeleteMapping
	public String removeDatasource(String name) {
		ds.removeDataSource(name);
		return "delete success";
	}
	
	
	@PostMapping("/add")
	public Set<String> add(@Validated @RequestBody DataSourceDTO dto) {
		
		DataSource dataSource = dataSourceCreator.createDataSource(fillSourceProperty(dto));
		return addDataSource(ds, dataSource, dto.getPollName());
	}
	
	@PostMapping("/add/basic")
	public Set<String> addBasic(@Validated @RequestBody DataSourceDTO dto) {
		DataSource dataSource = basicDataSourceCreator.createDataSource(fillSourceProperty(dto));
		return addDataSource(ds, dataSource, dto.getPollName());
	}
	
	@PostMapping("/add/druid")
	public Set<String> addDruid(@Validated @RequestBody DataSourceDTO dto) {
		DataSource dataSource = druidDataSourceCreator.createDataSource(fillSourceProperty(dto));
		return addDataSource(ds, dataSource, dto.getPollName());
	}
	
	@PostMapping("/add/hikari")
	public Set<String> addHikariCP(@Validated @RequestBody DataSourceDTO dto) {
		// DataSourceProperty dataSourceProperty = new DataSourceProperty();
		// BeanUtils.copyProperties(dto, dataSourceProperty);
		// DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
		// ds.addDataSource(dto.getPollName(), dataSource);
		// return ds.getCurrentDataSources().keySet();
		
		DataSource dataSource = hikariDataSourceCreator.createDataSource(fillSourceProperty(dto));
		return addDataSource(ds, dataSource, dto.getPollName());
	}
	
	/**
	 * 添加数据源
	 */
	private Set<String> addDataSource(DynamicRoutingDataSource ds, DataSource dataSource, String pollName) {
		
		ds.addDataSource(pollName, dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	private DataSourceProperty fillSourceProperty(DataSourceDTO dto) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		BeanUtils.copyProperties(dto, dataSourceProperty);
		return dataSourceProperty;
	}
	
	
	@PostMapping("/addJndi")
	public Set<String> addJndi(String pollName, String jndiName) {
		DataSource dataSource = jndiDataSourceCreator.createDataSource(jndiName);
		ds.addDataSource(pollName, dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	
}
