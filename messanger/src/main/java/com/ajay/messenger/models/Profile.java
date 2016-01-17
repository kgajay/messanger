package com.ajay.messenger.models;

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
@Table(name = "profiles")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {

	@Id
	@SequenceGenerator(name = "PROFILE_ID_GENERATOR", sequenceName = "profile_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PROFILE_ID_GENERATOR")
    @Column(name = "id")
	private long profileId;
	
	@Column(name = "profile_name")
	private String profileName;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Embedded
	private RecordTracker recordTracker;
	
}
