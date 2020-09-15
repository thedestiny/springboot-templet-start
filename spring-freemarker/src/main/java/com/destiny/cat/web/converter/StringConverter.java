package com.destiny.cat.web.converter;

import org.springframework.core.convert.converter.Converter;

public class StringConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		return source == null ? "" : source.trim();
	}

}
