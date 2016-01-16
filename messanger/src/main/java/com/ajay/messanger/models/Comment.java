package com.ajay.messanger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "comments")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comment {

	@Id
	@SequenceGenerator(name = "COMMENT_ID_GENERATOR", sequenceName = "comment_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "COMMENT_ID_GENERATOR")
    @Column(name = "id", nullable = false)
	private long commentId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "message_id")
	private Message message;
	
	private String comment;
	
	private String author;
	
	private RecordTracker recordTracker;
	
}
