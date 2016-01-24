package com.ajay.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ajay.messenger.dto.ProfileDTO;
import com.ajay.messenger.models.Profile;
import com.ajay.messenger.services.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public class ProfileResource {

	private ProfileService profileService;
	
	public ProfileResource(ProfileDTO profileDTO) {
		profileService = new ProfileService(profileDTO);
	}
	
	@GET
    public List<Profile> getAllProfiles() {
		
		return profileService.listProfiles();
		
	}
	
	@POST
	public Profile addProfile(Profile profile) {
		
		return profileService.addProfile(profile);
		
	}
	
	@PUT
	@Path("/{profileName}")
	public Profile editProfile(Profile profile, @PathParam("profileName") String profileName) {
		profile.setProfileName(profileName);
		return profileService.updateProfile(profile);
		
	}
	
	@GET
	@Path("/{profileName}")
    public Profile getProfile(@PathParam("profileName") String profileName) {
		
		return profileService.getProfile(profileName);
		
	}
	
	@DELETE
	@Path("/{profileName}")
    public void deleteProfile(@PathParam("profileName") String profileName) {
		
		profileService.removeProfile(profileName);
		
	}
}
