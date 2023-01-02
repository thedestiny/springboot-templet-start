package com.sk.manage.config.converter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateConverter implements Converter<String, Date> {


	public static String[] parsePatterns = {
			"yyyy-MM-dd" , "yyyy-MM-dd HH:mm:ss" , "yyyy-MM-dd HH:mm" , "yyyy-MM" ,
			"yyyy/MM/dd" , "yyyy/MM/dd HH:mm:ss" , "yyyy/MM/dd HH:mm" , "yyyy/MM" ,
			"yyyy.MM.dd" , "yyyy.MM.dd HH:mm:ss" , "yyyy.MM.dd HH:mm" , "yyyy.MM"};

	@Override
	public Date convert(String source) {
		try {
			return DateUtils.parseDate(source, parsePatterns);
		} catch (Exception e) {
			return new Date();
		}
	}

}
