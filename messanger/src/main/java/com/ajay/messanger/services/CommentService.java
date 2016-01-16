package com.ajay.messanger.services;

import java.util.ArrayList;
import java.util.List;

import com.ajay.messanger.domain.CommentResponse;
import com.ajay.messanger.domain.MessageResponse;
import com.ajay.messanger.dto.CommentDTOImpl;
import com.ajay.messanger.dto.MessageDTOImpl;
import com.ajay.messanger.models.Comment;
import com.ajay.messanger.models.Message;

public class CommentService {

	private CommentDTOImpl commentDTOImpl;
	private MessageDTOImpl messageDTOImpl;
	
	public CommentService(){
		commentDTOImpl = new CommentDTOImpl();
		messageDTOImpl = new MessageDTOImpl();
	}
	
	public List<Comment> getAllComments() {
		return commentDTOImpl.listComments();
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
		List<Comment> cmnts = commentDTOImpl.listComments(messageId, offset, size);
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
		Comment cmnt = commentDTOImpl.getComment(id);
		return this.commentResponse(cmnt);
	}
	
	public Comment addComment(long messageId, Comment cmnt){
		Message msg = messageDTOImpl.getMessage(messageId);
		cmnt.setMessage(msg);
		commentDTOImpl.addComment(cmnt);
		return cmnt;
		
	}
	
	public Boolean removeComment(long id){
		
		return commentDTOImpl.deleteComment(id);
		
	}
	
}
