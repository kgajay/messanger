package com.ajay.messenger.configs;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceConfig {

	private String authSalt;
	private List<String> whiteListedApis;
	private List<String> securedApis;
	private Map<String, String> paytm;
	private Map<String, String> mobikwik;
}
