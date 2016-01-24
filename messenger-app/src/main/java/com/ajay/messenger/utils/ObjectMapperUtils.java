package com.ajay.messenger.utils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ObjectMapperUtils {

	private static final ObjectMapper objectMapperDefault = new ObjectMapper();
    private static final ObjectMapper objectMapperConvertStyle = new ObjectMapper();

    public static ObjectMapper getObjectMapper(){
    	return objectMapperDefault;
    }
    
    public static ObjectMapper getObjectMapperConvertStyle(){
    	return objectMapperConvertStyle;
    }
    
}
