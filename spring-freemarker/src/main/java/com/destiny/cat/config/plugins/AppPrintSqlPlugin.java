package com.destiny.cat.config.plugins;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Intercepts({
		@Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
		@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class AppPrintSqlPlugin implements Interceptor {
	
	// Executor -> ParameterHandler -> StatementHandler -> ResultSetHandler
	//  org.apache.ibatis.logging.stdout.StdOutImpl
	
	/**
	 * 生成主键
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		System.out.println("invocation" + invocation);
		Object[] args = invocation.getArgs();
		MappedStatement statement = (MappedStatement) args[0];
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		// statement.getBoundSql()
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql(); //获取到SQL 注意当前SQL是没有被设置参数过的SQL
		log.info("printSql {}", printSql(sql, boundSql));
		return invocation.proceed();
	}
	
	private String getSql(Configuration configuration, BoundSql boundSql) {
		
		String sql = boundSql.getSql();
		if (StrUtil.isBlank(sql)) {
			return "";
		}
		
		sql = beautifySql(sql);
		
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		
		if (CollUtil.isNotEmpty(parameterMappings) && ObjectUtil.isNotNull(parameterObject)) {
			
			TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
			if (registry.hasTypeHandler(parameterObject.getClass())) {
				sql = replaceSql(sql, parameterObject);
			} else {
				MetaObject metaObject = configuration.newMetaObject(parameterObject);
				for (ParameterMapping mapping : parameterMappings) {
					String property = mapping.getProperty();
					if (metaObject.hasGetter(property)) {
						Object value = metaObject.getValue(property);
						sql = replaceSql(sql, value);
					} else if (boundSql.hasAdditionalParameter(property)) {
						Object additionalParameter = boundSql.getAdditionalParameter(property);
						sql = replaceSql(sql, additionalParameter);
					}
				}
			}
		}
		
		return sql;
		
	}
	
	private String beautifySql(String sql) {
		return sql;
	}
	
	
	/**
	 * 替换sql
	 */
	private String replaceSql(String sql, Object parameters) {
		
		String result;
		if (parameters instanceof String) {
			result = "'" + parameters.toString() + "'";
		} else if (parameters instanceof Date) {
			result = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parameters) + "'";
		} else {
			result = parameters.toString();
		}
		return result.replaceFirst("\\?", result);
		
	}
	
	
	/**
	 * 拦截sql后的具体实现
	 *
	 * @param sql
	 * @param boundSql
	 */
	public String printSql(String sql, BoundSql boundSql) {
		// 大家可以打一打断点，看看这些变量里面的参数的值，然后就会比较明朗了
		sql = sql.replaceAll("\\n", "");
		sql = sql.replaceAll("\\t", "");
		sql = sql.replaceAll("  ", " ");
		// 分割SQL
		String[] sqlPieces = sql.split(" ");
		String sqlType = sqlPieces[0].toLowerCase();
		String tableName = null;
		// 判断类型且获取table
		switch (sqlType) {
			case "select":
				return "";
			case "insert":
			case "delete":
				tableName = sqlPieces[2];
				break;
			case "update":
				tableName = sqlPieces[1];
				break;
		}
		if (tableName.equals("admin_user_log")) {
			return "";
		}
		// 继续分割SQL
		String[] sqlPiecesTwo = sql.split("\\?");
		// 获取真实sql
		sql = "";
		int t = 0;
		// 获取钥匙
		List<ParameterMapping> keys = boundSql.getParameterMappings();
		// 获取值
		Map<String, Object> value = JSONObject.parseObject(JSONObject.toJSONString(boundSql.getAdditionalParameter("_parameter")));
		for (ParameterMapping Key : keys) {
			String[] ks = Key.getProperty().split("\\.");
			String param = "";
			if (ks.length == 1) {
				param = String.valueOf(value.get(ks[0]).toString());
			} else if (ks.length == 2) {
				Map<String, Object> temp = JSONObject.parseObject(JSONObject.toJSONString(value.get(ks[0])));
				param = String.valueOf(temp.get(ks[1]).toString());
			} else if (ks.length == 3) {
				Map<String, Object> temp = JSONObject.parseObject(JSONObject.toJSONString(value.get(ks[0])));
				temp = JSONObject.parseObject(JSONObject.toJSONString(temp.get(ks[1])));
				param = String.valueOf(temp.get(ks[2]).toString());
			}
			if (param.equals("")) {
				param = "null";
			}
			sqlPiecesTwo[t] += param;
			t++;
		}
		return sql;
	}
	
}
