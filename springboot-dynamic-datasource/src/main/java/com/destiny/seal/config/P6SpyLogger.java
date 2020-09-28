package com.destiny.seal.config;

import cn.hutool.core.util.StrUtil;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class P6SpyLogger implements MessageFormattingStrategy {
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	@Override
	public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
		
		log.info(" execute now is {} url is {} sql is {}", now, url, sql.replaceAll("\n", ""));
		return !"".equals(sql.trim()) ? this.format.format(new Date(Long.valueOf(now))) + " | took " + elapsed + "ms | " + category + " | connection " + connectionId + "\n " + sql.replaceAll("\n", "") + ";" : "";
	}
	
}
