package com.ajay.messanger.models;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;



@Entity
@Table(name = "messages")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

	@Id
	@SequenceGenerator(name = "MESSAGE_ID_GENERATOR", sequenceName = "message_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MESSAGE_ID_GENERATOR")
    @Column(name = "id")
	private long messageId;
	private String message;
	private String author;
	
	@Embedded
	private RecordTracker recordTracker;
	  
}