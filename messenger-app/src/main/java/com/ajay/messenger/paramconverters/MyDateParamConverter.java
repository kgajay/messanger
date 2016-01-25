package com.ajay.messenger.paramconverters;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Calendar;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

import com.ajay.messenger.domain.DateRequest;


public class MyDateParamConverter implements ParamConverterProvider {

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if(rawType.getName().equals(DateRequest.class.getName())) {
			return new ParamConverter<T>(){

				@Override
				public T fromString(String value) {
					
					Calendar requestedDate = Calendar.getInstance();
					if("tomorrow".equalsIgnoreCase(value)) {
						requestedDate.add(Calendar.DATE, 1);
					}else if("yesterday".equals(value)) {
						requestedDate.add(Calendar.DATE, -1);
					}
					DateRequest dateRequest = new DateRequest();
					dateRequest.setDate(requestedDate.get(Calendar.DATE));
					dateRequest.setMonth(requestedDate.get(Calendar.MONTH));
					dateRequest.setYear(requestedDate.get(Calendar.YEAR));
					
					return rawType.cast(dateRequest);
				}

				@Override
				public String toString(T value) {
					if (value == null) {
						return null;
					}
					return value.toString();
				}
				
			};
		}
		return null;
	}

}
