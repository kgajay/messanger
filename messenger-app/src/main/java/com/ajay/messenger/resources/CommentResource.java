package com.ajay.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import com.ajay.messenger.domain.CommentResponse;
import com.ajay.messenger.models.Comment;
import com.ajay.messenger.services.CommentService;

@Path("/messages/{messageId}/comments")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public class CommentResource {

	// sub resource of messages
	private final CommentService commentService;
	public CommentResource(CommentService commentService){
		this.commentService = commentService;
	}
	
	@GET
	public List<CommentResponse> listComments(@PathParam("messageId") long messageId,
									  @QueryParam("offset") int offset,
									  @QueryParam("size") int size) {
		
		return commentService.getAllCommentsForMessageId(messageId, offset, size);
	}
	
	@POST
	public Response addComment(@PathParam("messageId") long messageId,
							  @Context UriInfo uriInfo, Comment cmnt) {
		
		cmnt = commentService.addComment(messageId, cmnt);
		URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(cmnt.getCommentId())).build();
		
		return Response.created(uri)
					   .entity(cmnt)
					   .build();
	}
	
	
	@GET
	@Path("/{commentId}")
	public Response getComment(@PathParam("messageId") long messageId, 
							   @PathParam("commentId") long commentId) {
		
		CommentResponse cmnt = commentService.getComment(commentId);
		return Response.status(Status.OK)
				.entity(cmnt)
				.build();
	}
}
