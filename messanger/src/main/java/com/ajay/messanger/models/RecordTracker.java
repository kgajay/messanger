package com.ajay.messanger.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecordTracker {

	@Column(name = "created_at")
    private Date createdAt;
 
	@Column(name = "updated_at")
    private Date updatedAt;
	
	public RecordTracker() {
		createdAt = new Date();
		updatedAt = new Date();
	}
	
//	@Column(name = "created_at")
//	private Date createdAt;
//	
//	@Column(name = "updated_at")
//	private Timestamp updatedAt;
//	
	
	
//	@PreUpdate
//	@PrePersist
//	public void updateTimeStamps() {
//		updatedAt = new Timestamp(System.currentTimeMillis());
//	}
	
//	@PrePersist
//	protected void onCreate() {
//		createdAt = new java.util.Date();
//	}
//
//	@PreUpdate
//	protected void onUpdate() {
//		updatedAt = new Timestamp(System.currentTimeMillis());
//	}
}
