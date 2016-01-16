package com.ajay.messanger.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ajay.messanger.domain.CommentResponse;
import com.ajay.messanger.domain.MessageResponse;
import com.ajay.messanger.dto.MessageDTO;
import com.ajay.messanger.dto.MessageDTOImpl;
import com.ajay.messanger.models.Comment;
import com.ajay.messanger.models.Message;

public class MessageService {

	private MessageDTO messageDTO;
	
	public MessageService() {
		messageDTO = new MessageDTOImpl();
	}
	
	public MessageResponse generateResponse(Message msg) {
		
		MessageResponse msgResp = new MessageResponse();
		msgResp.setAuthor(msg.getAuthor());
		msgResp.setMessage(msg.getMessage());
		msgResp.setMessageId(msg.getMessageId());
		msgResp.setRecordTracker(msg.getRecordTracker());
		List<CommentResponse> cmnts = new ArrayList<CommentResponse>();
		for(Comment cmnt : msg.getComments()){
			CommentResponse cmntResp = new CommentResponse();
			cmntResp.setAuthor(cmnt.getAuthor());
			cmntResp.setComment(cmnt.getComment());
			cmntResp.setCommentId(cmnt.getCommentId());
			cmntResp.setRecordTracker(cmnt.getRecordTracker());
			cmnts.add(cmntResp);
		}
		msgResp.setComments(cmnts);
		
		return msgResp;
	}
	
	public List<MessageResponse> getAllMessages(int year, int offset, int size) {
		List<Message> messages = new ArrayList<Message>();
		if(year > 0){
			messages = this.getAllMessagesForYear(year);
		}else if (offset >= 0 && size > 0) {
			messages = this.getAllMessagesPaginated(offset, size);
		}
		messages = messageDTO.listMessages();
		
		List<MessageResponse> msgResponse = new ArrayList<MessageResponse>();
		
		for(Message msg : messages) {
			
			msgResponse.add(this.generateResponse(msg));
			
		}
		return msgResponse;
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<Message>();
		List<Message> messages = messageDTO.listMessages();
		Calendar cal = Calendar.getInstance();
		for(Message msg : messages){
			cal.setTime(msg.getRecordTracker().getCreatedAt());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(msg);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int offset, int size) {
		// TODO Auto-generated method stub
		return messageDTO.listMessages(offset, size);
	}
	
	public MessageResponse getMessage(long id) {
		
		Message msg = messageDTO.getMessage(id);
		
		return this.generateResponse(msg);
		
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
