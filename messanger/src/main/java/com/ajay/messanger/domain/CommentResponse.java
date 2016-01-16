package com.ajay.messanger.domain;

import com.ajay.messanger.models.RecordTracker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentResponse {

	private long commentId;
	
	private String comment;
	
	private String author;
	
	private RecordTracker recordTracker;
	
	private MessageResponse messageResponse;
	
}
