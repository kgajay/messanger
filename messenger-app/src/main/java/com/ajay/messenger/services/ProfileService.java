package com.ajay.messenger.services;

import java.util.List;

import com.ajay.messenger.dto.ProfileDTO;
import com.ajay.messenger.models.Profile;

public class ProfileService {

	private final ProfileDTO profileDTO;
	
	public ProfileService(ProfileDTO profileDTO) {
		this.profileDTO  = profileDTO;
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
