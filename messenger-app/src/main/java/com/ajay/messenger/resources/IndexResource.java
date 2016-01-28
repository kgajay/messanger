package com.ajay.messenger.resources;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ajay.messenger.configs.ServiceConfig;
import com.ajay.messenger.domain.Credential;
import com.ajay.messenger.domain.LoginCredential;
import com.codahale.metrics.annotation.Timed;
import com.ajay.messenger.domain.DateRequest;

@Path("/")
public class IndexResource {

	private final String template;
    private final String defaultName;
    private final ServiceConfig serviceConfig;

    public IndexResource(String template, String defaultName, ServiceConfig serviceConfig) {
        this.template = template;
        this.defaultName = defaultName;
        this.serviceConfig = serviceConfig;
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirectToIndex(@Context UriInfo uriInfo) {
    	System.out.println("Builder " + uriInfo.getAbsolutePathBuilder());
    	URI uri = uriInfo.getAbsolutePathBuilder().path("index").build();
    	System.out.println("URI " + uri.toString());
		return Response.temporaryRedirect(uri)
					   .build();
    }
    
    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/index")
    public String index() {
    	System.out.println(serviceConfig.getMobikwik().get("host"));
    	return String.format(template, serviceConfig.getMobikwik().get("host")) + defaultName;
    }
    
    
    @GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("secured")
	public String securedMethod() {
	
		return "This is secured api method login";
	}
    
    @GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/get-date")
	public Date changeToDate(){
		
		return Calendar.getInstance().getTime();
		
	}
	
	@GET
	@Produces("text/shortdate")
	@Path("/get-media-type")
	public Date getMediaType(){
		
		return Calendar.getInstance().getTime();
		
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/accept-date/{dateString}")
	public String getRequestedDate(@PathParam("dateString") DateRequest dateString){
		
		return "Got " + dateString.toString();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("date-string")
	public String dateToString(@DefaultValue ("today") @QueryParam("date") DateRequest dateRequest) {
		return "Got " + dateRequest.toString();
	}
	
	@GET
    @Path("/metrix-param")
    @Produces(MediaType.TEXT_PLAIN)
    public String metrixParam(@MatrixParam("param") String metrixParam,
    						  @HeaderParam("customHeader") String customHeader,
    						  @CookieParam("cookie") String cookieParam) {
        return "Metrix param is: " + metrixParam + " Header param: " + customHeader + " Cookie Param " + cookieParam;
    }
    
    @GET
    @Path("/context")
    public String getParameterUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
    	String absPath =  uriInfo.getAbsolutePath().toString();
    	String header = httpHeaders.getRequestHeaders().toString();
    	return "absloute path: " + absPath + " requestedHeaders: " + header +
    			" particuler header: " + httpHeaders.getRequestHeader("content-type").toString();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	@Path("/login")
	public String login(LoginCredential loginCredential) {
        System.out.println("incoming loginCredential: " + loginCredential.getUser() + " ;pswd: " + loginCredential.getPassword());
        if(Credential.verifyUser(loginCredential)) {
            LoginCredential lc = Credential.searchUser(loginCredential.getUser());
            if(lc.getAuthToken() != null) {
                System.out.println("AuthToken present: " + lc.getAuthToken());
            }else {
                System.out.println("Create AuthToken");
                Credential.issueToken(lc, serviceConfig.getAuthSalt());
            }
            return "User: " + lc.getUser() + " Pswd: " + lc.getPassword() + " authToken: " + lc.getAuthToken();
        }
        return "User not present";
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/sign-up")
    public String signUp(LoginCredential f) {
	
	    return "login api : " + serviceConfig.getAuthSalt() + " Form: " + f.getUser();
    
    }


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get-user-info")
    public String getUserInfo() {
        return "login api : " + serviceConfig.getAuthSalt();
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/get-template")
    public String getTemplate() {
        return "login api : " + String.format(template, defaultName);
    }

}
