package com.ajay.messanger.domain;

import javax.ws.rs.QueryParam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageRequest {

	private @QueryParam("year") int year;
	private @QueryParam("offset") int offset;
	private @QueryParam("size") int size;
	
}
