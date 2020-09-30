package com.destiny.rabbit.config.typehandler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;


/**
 * type handler
 * 拦截数据库中时间类型 如果时间 timestamp为0,
 * 则将对应的时间设置为null
 * */
// DateTypeHandler
@Slf4j
@MappedJdbcTypes(value = JdbcType.TIMESTAMP, includeNullJdbcType = true)
@MappedTypes(Date.class)
public class DateTimeTypeHandler extends BaseTypeHandler<Date> {
	
	
	@Override
	public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			parameter = new Date(0);
		}
		
		try {
			setNonNullParameter(ps, i, parameter, jdbcType);
		} catch (Exception e) {
			throw new TypeException("Error setting non null for parameter #" + i + " with JdbcType " + jdbcType + " . "
					+ "Try setting a different JdbcType for this parameter or a different configuration property. "
					+ "Cause: " + e, e);
		}
	}
	
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType)
			throws SQLException {
		
		DateFormat df = DateFormat.getDateInstance();
		String dateStr = df.format(parameter);
		ps.setDate(i, java.sql.Date.valueOf(dateStr));
	}
	
	@Override
	public Date getNullableResult(ResultSet rs, String columnName) throws SQLException {
		java.sql.Date sqlDate = rs.getDate(columnName);
		if (judgeDate(sqlDate)) {
			return new Date(sqlDate.getTime());
		}
		return null;
	}
	
	@Override
	public Date getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		System.out.println(columnIndex);
		java.sql.Date sqlDate = rs.getDate(columnIndex);
		if (judgeDate(sqlDate)) {
			return new Date(sqlDate.getTime());
		}
		return null;
	}
	
	@Override
	public Date getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		java.sql.Date sqlDate = cs.getDate(columnIndex);
		if (judgeDate(sqlDate)) {
			return new Date(sqlDate.getTime());
		}
		return null;
	}
	
	/**
	 * 判断是否为1970年默认时间,默认时间置为null
	 */
	private boolean judgeDate(java.sql.Date sqlDate) {
		return sqlDate != null && sqlDate.getTime() > 0L;
	}
	
	
}
