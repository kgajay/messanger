package com.ajay.messenger.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;

import com.ajay.messenger.domain.ErrorResponse;

//@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable ex) {
		ErrorResponse err = new ErrorResponse(ex.getMessage(), 500, "http://vhsjasakl");
		return Response.status(Status.NOT_FOUND)
					   .entity(err)
					   .build();
		
	}

}
