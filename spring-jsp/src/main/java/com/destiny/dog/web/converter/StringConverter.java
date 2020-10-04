package com.destiny.dog.web.converter;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {
	
	@Override
	public String convert(String source) {
		
		return StrUtil.isBlank(source) ? "" : source.trim();
	}
	
}
