package com.ajay.messanger.dto;

import java.util.List;

import com.ajay.messanger.models.Profile;

public interface ProfileDTO {

	public long createProfile(Profile profile);
	
	public Boolean updateProfile(Profile profile);
	
	public Profile getProfile(String profileName);
	
	public List<Profile> listProfiles();
	
	public Boolean deleteProfile(String profileName);
}
