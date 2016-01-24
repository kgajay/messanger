package com.ajay.messenger.services;

import java.util.ArrayList;
import java.util.List;

import com.ajay.messenger.domain.CommentResponse;
import com.ajay.messenger.domain.MessageResponse;
import com.ajay.messenger.dto.CommentDTO;
import com.ajay.messenger.dto.MessageDTO;
import com.ajay.messenger.models.Comment;
import com.ajay.messenger.models.Message;

public class CommentService {

	private final CommentDTO commentDTO;
	private final MessageDTO messageDTO;
	
	public CommentService(CommentDTO commentDTO, MessageDTO messageDTO){
		this.commentDTO = commentDTO;
		this.messageDTO = messageDTO;
	}
	
	public List<Comment> getAllComments() {
		return commentDTO.listComments();
	}
	
	public CommentResponse commentResponse(Comment cmnt) {
		CommentResponse cmntResp = new CommentResponse();
		cmntResp.setAuthor(cmnt.getAuthor());
		cmntResp.setComment(cmnt.getComment());
		cmntResp.setCommentId(cmnt.getCommentId());
		cmntResp.setRecordTracker(cmnt.getRecordTracker());
		Message msg = cmnt.getMessage();
		cmntResp.setMessageResponse(new MessageResponse());
		cmntResp.getMessageResponse().setAuthor(msg.getAuthor());
		cmntResp.getMessageResponse().setMessage(msg.getMessage());
		cmntResp.getMessageResponse().setMessageId(msg.getMessageId());
		cmntResp.getMessageResponse().setRecordTracker(msg.getRecordTracker());
		
		return cmntResp;
	}
	
	public List<CommentResponse> getAllCommentsForMessageId(long messageId, int offset, int size) {
		List<Comment> cmnts = commentDTO.listComments(messageId, offset, size);
		List<CommentResponse> cmntResps = new ArrayList<CommentResponse>();
		for(Comment cmnt : cmnts) {
			
			cmntResps.add(this.commentResponse(cmnt));
		}
		return cmntResps;
	}
	
	public Comment updateComment(long messageId, long commentId, Comment cmnt) {
		
		return null;
	}
	
	public CommentResponse getComment(long id) {
		Comment cmnt = commentDTO.getComment(id);
		return this.commentResponse(cmnt);
	}
	
	public Comment addComment(long messageId, Comment cmnt){
		Message msg = messageDTO.getMessage(messageId);
		cmnt.setMessage(msg);
		commentDTO.addComment(cmnt);
		return cmnt;
		
	}
	
	public Boolean removeComment(long id){
		
		return commentDTO.deleteComment(id);
		
	}
	
}
