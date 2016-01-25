package com.ajay.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ajay.messenger.domain.CommentResponse;
import com.ajay.messenger.domain.MessageRequest;
import com.ajay.messenger.domain.MessageResponse;
import com.ajay.messenger.models.Message;
import com.ajay.messenger.services.MessageService;



@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {

	private final MessageService messageService;
	
	public MessageResource(MessageService messageService) {
		this.messageService  = messageService;
	}
	
	
//	@GET
//    public List<Message> getAllMessages(@QueryParam("year") int year,
//    									@QueryParam("offset") int offset,
//										@QueryParam("size") int size) {
//		
//		return messageService.getAllMessages(year, offset, size);
//		
//	}
	
	@GET
    public List<MessageResponse> getAllMessages(@BeanParam MessageRequest messageRequest) {
		
		return messageService.getAllMessages(messageRequest.getYear(), messageRequest.getOffset(), messageRequest.getSize());
		
	}
	
	@POST
	public Message addMessage(Message msg) {
		
		return messageService.addMessage(msg);
		
	}
	
	@PUT
	@Path("/{messageId}")
    public Message updateMessage(@PathParam("messageId") long id, Message msg) {
		
		return messageService.updateMessage(id, msg);
		
	}
	
	@DELETE
	@Path("/{messageId}")
    public void deleteMessage(@PathParam("messageId") long messageId) {
		
		messageService.deleteMessage(messageId);
		
	}
	
	@GET
	@Path("/{messageId}")
    public MessageResponse getMessage(@PathParam("messageId") long id) {
		
		MessageResponse msg = messageService.getMessage(id);
		System.out.println(msg.getComments());
		for(CommentResponse cmnt : msg.getComments()) {
			System.out.println(cmnt.getComment());
		}
		return msg;
		
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{messageId}/comments-count")
	public long getCommentsCount(@PathParam("messageId") long id) {
		return messageService.getTotalCommentsCount(id);
	}
}
