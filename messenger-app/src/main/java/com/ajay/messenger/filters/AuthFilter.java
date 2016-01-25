package com.ajay.messenger.filters;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

@Provider
public class AuthFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
	private static final String SECURED_URI = "secured";
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		if(requestContext.getUriInfo().getPath().contains(SECURED_URI)) {
			List<String> authHeaders = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
			
			if (authHeaders != null && authHeaders.size() > 0) {
				String authToken = authHeaders.get(0);
				authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
				String decodedString = Base64.decodeAsString(authToken);
				StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
				String username = tokenizer.nextToken();
				String password = tokenizer.nextToken();
				
				if(username.equals("test") && password.equals("test")) {
					return;
				}
			}
			
			Response unauthorizedresponse = Response
											.status(Response.Status.UNAUTHORIZED)
											.entity("user can not access")
											.build();
			
			requestContext.abortWith(unauthorizedresponse);
		}
	}

}
