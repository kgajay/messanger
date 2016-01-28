package com.ajay.messenger.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginCredential {

	private String user;
	private String password;
	private String authToken;

	public LoginCredential() {

	}

	public LoginCredential(String user, String pswd) {
		this.user = user;
		this.password = pswd;
	}
}
