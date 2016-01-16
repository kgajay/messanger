package com.ajay.messanger.dto;

import java.util.List;

import com.ajay.messanger.models.Comment;
import com.ajay.messanger.models.Message;

public interface MessageDTO {

	public long insertMessage(Message msg);
	
	public Boolean updateMessage(Message msg);
	
	public Message getMessage(long id);
	
	public List<Message> listMessages();
	
	public List<Message> listMessages(int offset, int size);
	
	public Boolean deleteMessage(long id);

	public List<Comment> getComments(Message msg);
	
}
