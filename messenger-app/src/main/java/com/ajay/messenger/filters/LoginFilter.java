package com.ajay.messenger.filters;

import java.io.*;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;

import com.ajay.messenger.configs.ServiceConfig;
import com.ajay.messenger.domain.Credential;
import com.ajay.messenger.domain.LoginCredential;
import com.ajay.messenger.utils.JsonUtil;
import org.apache.commons.io.IOUtils;

import org.json.JSONObject;

public class LoginFilter implements ContainerRequestFilter, ContainerResponseFilter{

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

	private final ServiceConfig serviceConfig;

	public LoginFilter(ServiceConfig serviceConfig) {
		
		this.serviceConfig = serviceConfig;
	
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

        String uriPath = requestContext.getUriInfo().getPath();
        String keyName = "";
        if(serviceConfig.getWhiteListedApis().contains(uriPath)) {
            return;
        }else {
            if(serviceConfig.getSecuredApis().contains(uriPath)) {
                if(requestContext.getMethod().equalsIgnoreCase("POST")) {
                    StringWriter writer = new StringWriter();
                    IOUtils.copy(requestContext.getEntityStream(), writer, "UTF-8");
                    String requestBody = writer.toString();
                    System.out.println("Request body: " + requestBody);

                    JSONObject jsonObj = new JSONObject(requestBody);
                    Map<String, Object> reqParams = JsonUtil.jsonToMap(jsonObj);

                    System.out.println("Request body key val: " + reqParams.get("user"));
                    requestContext.setEntityStream(new ByteArrayInputStream(requestBody.getBytes()));
                    keyName = (String) reqParams.get("user");
                }else if(requestContext.getMethod().equalsIgnoreCase("GET")){
                    keyName = requestContext.getUriInfo().getQueryParameters().get("user").get(0);
                }
                if(authenticate(requestContext, keyName)) {
                    return;
                }
            }

        }

		Response unauthorizedResponse = Response
				.status(Response.Status.UNAUTHORIZED)
				.entity("user can not access")
				.build();

		requestContext.abortWith(unauthorizedResponse);
		
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
//		System.out.println("Response Filter Headers: " + requestContext.getHeaders());
	}

    private boolean authenticate(ContainerRequestContext requestContext, String secureKey) {

        List<String> authHeaders = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
        if (authHeaders != null && authHeaders.size() > 0) {
            String authToken = authHeaders.get(0);
            LoginCredential lc = null;
            lc = Credential.searchUser(secureKey);
            if(lc != null) {
                System.out.println("Authenticate Token: " + lc.getAuthToken());
                return authToken.equals(lc.getAuthToken());
            }
        }
        return false;
    }
}
