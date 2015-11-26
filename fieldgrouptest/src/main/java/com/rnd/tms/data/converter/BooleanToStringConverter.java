package com.rnd.tms.data.converter;

import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class BooleanToStringConverter implements Converter<String, Boolean> {

	@Override
	public Boolean convertToModel(String value,
			Class<? extends Boolean> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		Boolean returnValue=null;
		
		if(value!=null){
			if(value.equalsIgnoreCase("Yes")){
				returnValue =  true;
			}else if(value.equalsIgnoreCase("No")){
				returnValue =  false;
			}
		}
		return returnValue;
	}

	@Override
	public String convertToPresentation(Boolean value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		String returnValue = "";
		
		if(value!=null){
			if(value){
				returnValue="Yes";
			}else{
				returnValue="No";
			}
		}
		return returnValue;
	}

	@Override
	public Class<Boolean> getModelType() {
		return Boolean.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
