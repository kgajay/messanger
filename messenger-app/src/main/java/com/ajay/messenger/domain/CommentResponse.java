package com.ajay.messenger.domain;

import com.ajay.messenger.models.RecordTracker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSnakeCase
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentResponse {

	private long commentId;
	
	private String comment;
	
	private String author;
	
	private RecordTracker recordTracker;
	
	private MessageResponse messageResponse;
	
}
