package com.rnd.tms.data.converter;

import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.vaadin.data.util.converter.Converter;

public class JodaDateTimeToJavaDate implements Converter<Date, DateTime> {
	
	public static final String pattern = "E MM/dd/yyyy HH:mm";
	
	@Override
	public Class<DateTime> getModelType() {
		return DateTime.class;
	}

	@Override
	public Class<Date> getPresentationType() {
		return Date.class;
	}

	@Override
	public DateTime convertToModel(Date javaDate,Class<? extends DateTime> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		if(javaDate==null){
			return null;
		}
		return new DateTime(javaDate);
	}

	@Override
	public Date convertToPresentation(DateTime value,
			Class<? extends Date> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if(value==null){
			return null;
		}
		
		return value.toDate();
	}
}