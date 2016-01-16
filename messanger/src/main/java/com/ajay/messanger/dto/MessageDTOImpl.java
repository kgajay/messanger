package com.ajay.messanger.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ajay.messanger.models.Message;
import com.ajay.messanger.models.RecordTracker;


@SuppressWarnings({"unchecked", "deprecation"})
public class MessageDTOImpl implements MessageDTO{

	private static SessionFactory sessionFactory;
	
	public MessageDTOImpl() {
		
		try{
			 sessionFactory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		
	}
	
	@Override
	public long insertMessage(Message msg){
		
		long retVal = 0;
		
		msg.setRecordTracker(new RecordTracker());
		Session session = sessionFactory.openSession();
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
		session.close();
		return msg;
	}
	
	@Override
	public List<Message> listMessages(){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Message");
		List<Message> msgs = new ArrayList<Message>();
		msgs = query.list();
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
}
