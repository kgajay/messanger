package com.ajay.messanger.services;

import java.util.List;

import com.ajay.messanger.dto.ProfileDTO;
import com.ajay.messanger.dto.ProfileDTOImpl;
import com.ajay.messanger.models.Profile;

public class ProfileService {

	private ProfileDTO profileDTO;
	
	public ProfileService() {
		profileDTO  = new ProfileDTOImpl();
	}
	
	public List<Profile> listProfiles() {
		return profileDTO.listProfiles();
	}
	
	public Profile getProfile(String profileName) {
		return profileDTO.getProfile(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		
		if(profileDTO.createProfile(profile) > 0) {
			return profile;
		}
		return null;
	}
	
	public Profile updateProfile(Profile profile) {
		
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		Profile existingProfile = profileDTO.getProfile(profile.getProfileName());
		profile.setProfileId(existingProfile.getProfileId());
		profile.setRecordTracker(existingProfile.getRecordTracker());
		if(profileDTO.updateProfile(profile)){
			return profile;
		}
		return null;
	}

	public void removeProfile(String profileName) {
		profileDTO.deleteProfile(profileName);
	}
}
