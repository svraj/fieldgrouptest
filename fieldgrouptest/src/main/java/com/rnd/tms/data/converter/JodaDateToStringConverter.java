package com.rnd.tms.data.converter;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.vaadin.data.util.converter.Converter;

public class JodaDateToStringConverter implements Converter<String, DateTime> {
	
	public static final String PATTERN = "E MM/dd/yyyy HH:mm";
	
	@Override
	public Class<DateTime> getModelType() {
		return DateTime.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

	@Override
	public DateTime convertToModel(String value,Class<? extends DateTime> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(PATTERN);
		if(value==null||value.equalsIgnoreCase("")){
			return null;
		}
		return fmt.parseDateTime(value);
	}

	@Override
	public String convertToPresentation(DateTime value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if(value==null){
			return "";
		}
		
		return value.toString(PATTERN,locale);
	}
}