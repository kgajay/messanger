package com.ajay.messanger.services;

import java.util.ArrayList;
import java.util.List;

import com.ajay.messanger.dto.MessageDTO;
import com.ajay.messanger.dto.MessageDTOImpl;
import com.ajay.messanger.models.Message;

public class MessageService {

	private MessageDTO messageDTO;
	
	public MessageService() {
		messageDTO = new MessageDTOImpl();
	}
	
	public List<Message> getAllMessages() {
		// TODO Auto-generated method stub
		return messageDTO.listMessages();
	}

	public List<Message> getAllMessagesForYear(int year) {
		// TODO Auto-generated method stub
		List<Message> messagesForYear = new ArrayList<Message>(); 
		return messageDTO.listMessages();
	}
	
	public List<Message> getAllMessagesPaginated(int offset, int size) {
		// TODO Auto-generated method stub
		return messageDTO.listMessages();
	}
	
	public Message getMessage(long id) {
		return messageDTO.getMessage(id);
	}
	
	public Message updateMessage(long messageId, Message msg) {
		if(messageId == 0){
			return null;
		}
		Message oldMessage = messageDTO.getMessage(messageId);
		if(msg.getAuthor() != null) {
			oldMessage.setAuthor(msg.getAuthor());
		}
		if(msg.getMessage() != null) {
			oldMessage.setMessage(msg.getMessage());
		}
		Boolean isUpdated = messageDTO.updateMessage(oldMessage);
		if(isUpdated) {
			return oldMessage;
		}
		return null;
	}
	
	public Message addMessage(Message msg) {
		messageDTO.insertMessage(msg);
		return msg;
	}
	
	public Boolean deleteMessage(long id) {
		return messageDTO.deleteMessage(id);
	}
}
