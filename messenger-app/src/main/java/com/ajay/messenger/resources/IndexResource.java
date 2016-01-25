package com.ajay.messenger.resources;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ajay.messenger.configs.ServiceConfig;
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
}
