package com.ajay.messenger.dto;

import java.util.List;

import com.ajay.messenger.models.Comment;

public interface CommentDTO {

	public long addComment(Comment cmnt);
	
	public Boolean updateComment(Comment cmnt);
	
	public Comment getComment(long id);
	
	public List<Comment> listComments();
	
	public Boolean deleteComment(long id);

	public List<Comment> listComments(long messageId, int offset, int size);
}
