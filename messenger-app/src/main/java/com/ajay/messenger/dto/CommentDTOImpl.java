package com.ajay.messenger.dto;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ajay.messenger.models.Comment;
import com.ajay.messenger.models.RecordTracker;

@SuppressWarnings({"unchecked"})
public class CommentDTOImpl implements CommentDTO {

	private final SessionFactory sessionFactory;
	
	public CommentDTOImpl( SessionFactory sessionFactory ) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long addComment(Comment cmnt) {
		long cmntId = 0;
		
		cmnt.setRecordTracker(new RecordTracker());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try{
			tx = (Transaction) session.beginTransaction();
			cmntId = (long) session.save(cmnt);
			tx.commit();
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.close();
		}
		
		return cmntId;
	}

	@Override
	public Boolean updateComment(Comment cmnt) {
		Boolean isUpdated = false;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			cmnt.getRecordTracker().setUpdatedAt(new Date());
			session.update(cmnt);
			tx.commit();
			isUpdated = true;
		}catch(Exception e) {
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace();
		}finally {
			session.flush();
			session.close();
		}
		return isUpdated;
	}

	@Override
	public Comment getComment(long id) {
		Session session = sessionFactory.openSession();
		Comment cmnt = (Comment) session.get(Comment.class, id);
		if(cmnt == null) {
//			throw new Exception("Comment with id " + id + " does not exists"); 
			return null;
		}
		session.close();
		return cmnt;
	}

	@Override
	public List<Comment> listComments() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Contact");
		List<Comment> cmnts = (List<Comment>) query.list();
		session.close();
		return cmnts;
	}

	@Override
	public List<Comment> listComments(long messageId, int offset, int size) {
		Session session = sessionFactory.openSession();
//		Query query = session.createQuery("from Comment where message=:messageId");
//		Message msg = (Message) session.get(Message.class, messageId);
//		query.setParameter("messageId", msg);
		
		Query query = session.createQuery("select comment from Comment as comment where message.messageId=:messageId");
		query.setParameter("messageId", messageId);
		if(offset >= 0 && size > 0) {
			query.setFirstResult(offset);
			query.setMaxResults(size);
		}
		System.out.println("Message ID query: " + query.getQueryString());
		List<Comment> cmnts = (List<Comment>) query.list();
		session.close();
		return cmnts;
	}

	@Override
	public Boolean deleteComment(long id) {
		Boolean isDeleted = false;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = (Transaction) session.beginTransaction();
			session.delete(session.get(Comment.class, id));
			tx.commit();
			isDeleted = true;
		}catch(Exception e) {
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace();
		}finally {
			session.flush();
			session.close();
		}
		return isDeleted;
	}

}
