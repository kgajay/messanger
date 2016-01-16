package com.ajay.messanger.dto;

import java.util.List;

import com.ajay.messanger.models.Message;

public interface MessageDTO {

	public long insertMessage(Message msg);
	
	public Boolean updateMessage(Message msg);
	
	public Message getMessage(long id);
	
	public List<Message> listMessages();
	
	public Boolean deleteMessage(long id);
	
}
