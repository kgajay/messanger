package com.ajay.messenger.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.ajay.messenger.models.Comment;
import com.ajay.messenger.models.Message;
import com.ajay.messenger.models.RecordTracker;


@SuppressWarnings({"unchecked", "unused"})
public class MessageDTOImpl implements MessageDTO{

	private final SessionFactory sessionFactory;
	
	public MessageDTOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long insertMessage(Message msg){
		
		long retVal = 0;
		Session session = sessionFactory.openSession();
		msg.setRecordTracker(new RecordTracker());
		Transaction tx = null;
		
		try{
			tx = (Transaction) session.beginTransaction();
			retVal = (long) session.save(msg);
			tx.commit();
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.close();
		}
		
		return retVal;
	}
	
	@Override
	public Boolean updateMessage(Message msg) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		Boolean isSuccesfull = false;
		try{
			tx = (Transaction) session.beginTransaction();
			msg.getRecordTracker().setUpdatedAt(new Date());
			session.update(msg);
			tx.commit();
			isSuccesfull = true;
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.close();
		}
		return isSuccesfull;
	}
	
	@Override
	public Message getMessage(long id) {
		Session session = sessionFactory.openSession();
		Message msg = (Message) session.get(Message.class, id);
		for(Comment cmnt : msg.getComments());
		session.close();
		return msg;
	}
	
	@Override
	public List<Message> listMessages(){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Message");
		List<Message> msgs = new ArrayList<Message>();
		try{
			msgs = query.list();
			for(Message msg : msgs){
//				msg.getComments();
				for(Comment cmnt : msg.getComments());
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return msgs;
	}
	
	@Override
	public List<Message> listMessages(int offset, int size){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Message");
		query.setFirstResult(offset);
		query.setMaxResults(size);
		System.out.println("Offset query: " + query.getQueryString());
		List<Message> msgs = new ArrayList<Message>();
		msgs = query.list();
		for(Message msg : msgs){
			for(Comment cmnt : msg.getComments());
		}
		session.close();
		return msgs;
	}
	
	@Override
	public Boolean deleteMessage(long id) {
		Boolean isDeleted = false;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try{
			tx = (Transaction) session.beginTransaction();
			session.delete(session.get(Message.class, id));
			tx.commit();
			isDeleted = true;
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.flush();
			session.close();
		}
		
		return isDeleted;
	}
	
	@Override
	public long getCommentsCount(long id) {
		long retVal = 0;
		Session session = sessionFactory.openSession();
		
		Criteria cr = session.createCriteria(Comment.class);
		cr.add(Restrictions.eq("message.messageId", id));
		cr.setProjection(Projections.rowCount());
		System.out.println("Criteria query: " + cr.toString());
		List<?> rowCount = cr.list();
		retVal = Long.valueOf((String) rowCount.get(0));
		session.close();
		return retVal;
		
	}
}
