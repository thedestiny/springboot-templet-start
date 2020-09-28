package com.destiny.seal.web;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.BasicDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.DruidDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.creator.JndiDataSourceCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/datasource")
public class DataSourceController {
	
	@Autowired
	private DynamicRoutingDataSource ds;
	// @Autowired
	// private DataSourceCreator dataSourceCreator;
	private BasicDataSourceCreator basicDataSourceCreator;
	private JndiDataSourceCreator jndiDataSourceCreator;
	private DruidDataSourceCreator druidDataSourceCreator;
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
	
	/*@PostMapping("/add") //recommond use this method
	public Set<String> add(@Validated @RequestBody DataSourceDTO dto) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		BeanUtils.copyProperties(dto, dataSourceProperty);
		DataSource dataSource = dataSourceCreator.createDataSource(dataSourceProperty);
		ds.addDataSource(dto.getPollName(), dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	@PostMapping("/addBasic")
	public Set<String> addBasic(@Validated @RequestBody DataSourceDTO dto) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		BeanUtils.copyProperties(dto, dataSourceProperty);
		DataSource dataSource = basicDataSourceCreator.createDataSource(dataSourceProperty);
		ds.addDataSource(dto.getPollName(), dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	@PostMapping("/addJndi")
	public Set<String> addJndi(String pollName, String jndiName) {
		DataSource dataSource = jndiDataSourceCreator.createDataSource(jndiName);
		ds.addDataSource(pollName, dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	@PostMapping("/addDruid")
	public Set<String> addDruid(@Validated @RequestBody DataSourceDTO dto) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		BeanUtils.copyProperties(dto, dataSourceProperty);
		DataSource dataSource = druidDataSourceCreator.createDataSource(dataSourceProperty);
		ds.addDataSource(dto.getPollName(), dataSource);
		return ds.getCurrentDataSources().keySet();
	}
	
	@PostMapping("/addHikariCP")
	public Set<String> addHikariCP(@Validated @RequestBody DataSourceDTO dto) {
		DataSourceProperty dataSourceProperty = new DataSourceProperty();
		BeanUtils.copyProperties(dto, dataSourceProperty);
		DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
		ds.addDataSource(dto.getPollName(), dataSource);
		return ds.getCurrentDataSources().keySet();
	}*/
	
	
}
