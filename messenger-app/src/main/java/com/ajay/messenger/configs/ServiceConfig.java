package com.ajay.messenger.configs;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceConfig {

	private Map<String, String> paytm;
	private Map<String, String> mobikwik;
}
