package com.ajay.messanger.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/")
public class IndexResource {

	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String index() {
        return "This is root response !!";
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
}
