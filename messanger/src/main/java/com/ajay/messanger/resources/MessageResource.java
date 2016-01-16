package com.ajay.messanger.resources;

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

import com.ajay.messanger.models.Message;
import com.ajay.messanger.services.MessageService;



@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON )
public class MessageResource {

	private MessageService messageService;
	
	public MessageResource() {
		messageService  = new MessageService();
	}
	
	
	@GET
    public List<Message> getAllMessages() {
		
		return messageService.getAllMessages();
		
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
    public Message getMessage(@PathParam("messageId") long id) {
		
		return messageService.getMessage(id);
		
	}
}
