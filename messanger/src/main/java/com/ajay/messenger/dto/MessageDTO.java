package com.ajay.messenger.dto;

import java.util.List;

import com.ajay.messenger.models.Comment;
import com.ajay.messenger.models.Message;

public interface MessageDTO {

	public long insertMessage(Message msg);
	
	public Boolean updateMessage(Message msg);
	
	public Message getMessage(long id);
	
	public List<Message> listMessages();
	
	public List<Message> listMessages(int offset, int size);
	
	public Boolean deleteMessage(long id);

	public List<Comment> getComments(Message msg);
	
}
