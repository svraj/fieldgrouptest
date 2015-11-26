package com.rnd.tms.data.converter;

import java.util.Locale;

import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import com.vaadin.data.util.converter.Converter;

public class JodaDurationToStringConverter implements Converter<String, Duration> {
	
	
	private PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
		.printZeroAlways()
	    .minimumPrintedDigits(2)
	    .appendHours()
	    .appendSeparator(":")
	    .appendMinutes()
	    .toFormatter();
		
	@Override
	public Class<Duration> getModelType() {
		return Duration.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

	@Override
	public Duration convertToModel(String value,Class<? extends Duration> targetType, Locale locale)
			throws ConversionException {
		
		if(value==null||value.equalsIgnoreCase("")){
			return null;
		}
		
		return periodFormatter.parsePeriod(value).toStandardDuration();
	}

	@Override
	public String convertToPresentation(Duration value,
			Class<? extends String> targetType, Locale locale)
			throws ConversionException {

		if(value==null){
			return "";
		}
		
		return periodFormatter.print(value.toPeriod());
	}
}