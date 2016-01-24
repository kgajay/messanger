package com.ajay.messenger.resources;

import java.net.URI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ajay.messenger.configs.ServiceConfig;
import com.codahale.metrics.annotation.Timed;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
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
    public Response redirectToIndex(@Context UriInfo uriInfo) {
    	System.out.println("Builder " + uriInfo.getAbsolutePathBuilder());
    	URI uri = uriInfo.getAbsolutePathBuilder().path("index").build();
    	System.out.println("URI " + uri.toString());
		return Response.temporaryRedirect(uri)
					   .build();
    }
    
    @GET
    @Timed
    @Path("/index")
    public String index() {
    	System.out.println(serviceConfig.getMobikwik().get("host"));
    	return String.format(template, serviceConfig.getMobikwik().get("host")) + defaultName;
    }
}
