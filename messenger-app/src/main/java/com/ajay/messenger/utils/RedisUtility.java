package com.ajay.messenger.utils;

import java.io.IOException;

import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.Jedis;

@Getter
@Setter
public class RedisUtility {

	private Jedis jedis;
	
	public RedisUtility(Jedis jedis) {
		this.jedis = jedis;
	}
	
	public boolean checkConnection() {
		return jedis.isConnected();
	}
	
	public void setData(String keyName, Object obj, @DefaultValue ("30") int seconds) {
		
		try {
			jedis.setex(keyName, seconds, ObjectMapperUtils.getObjectMapper().writeValueAsString(obj));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Object getData(String keyName, Class<?> t) {
		
		try {
			String val = jedis.get(keyName);
			if(val != null) {
				Object retObj = ObjectMapperUtils.getObjectMapper().readValue(val, t);
				return retObj;
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
