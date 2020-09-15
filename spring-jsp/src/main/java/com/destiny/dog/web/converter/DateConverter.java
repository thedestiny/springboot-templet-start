package com.destiny.dog.web.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

	private final DateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public Date convert(String source) {
		try {
			return dateFormat.parse(source);
		} catch (Exception e) {
			return null;
		}
	}

}
