package com.ajay.messenger.dto;

import java.util.List;

import com.ajay.messenger.models.Profile;

public interface ProfileDTO {

	public long createProfile(Profile profile);
	
	public Boolean updateProfile(Profile profile);
	
	public Profile getProfile(String profileName);
	
	public List<Profile> listProfiles();
	
	public Boolean deleteProfile(String profileName);
}
