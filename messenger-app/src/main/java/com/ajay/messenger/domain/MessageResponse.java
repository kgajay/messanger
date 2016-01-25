package com.ajay.messenger.domain;

import java.util.ArrayList;
import java.util.List;

import com.ajay.messenger.models.RecordTracker;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse {

	private long messageId;
	private String message;
	private String author;
	private List<CommentResponse> comments = new ArrayList<CommentResponse>();
	private long commentsCount;
	private RecordTracker recordTracker;
	
}
