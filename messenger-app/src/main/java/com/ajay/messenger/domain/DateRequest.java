package com.ajay.messenger.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DateRequest {

	private int date;
	private int month;
	private int year;
	
	@Override
	public String toString() {
		String mm = "";
		if(month < 9) {
			mm = "0" + (month+1);
		}else {
			mm = String.valueOf(month+1);
		}
		return "My Date date: " + date + " month: " + mm + " year: " + year;
	}
	
}
