package com.ajay.messanger.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.ajay.messanger.models.Profile;
import com.ajay.messanger.models.RecordTracker;

@SuppressWarnings({"unchecked", "deprecation"})
public class ProfileDTOImpl implements ProfileDTO{

	private static SessionFactory sessionFactory;
	
	public ProfileDTOImpl() {
		
		try{
			 sessionFactory = new Configuration().configure().buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }
		
	}
	
	@Override
	public long createProfile(Profile profile) {
		long retVal = 0;
		profile.setRecordTracker(new RecordTracker());
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try{
			tx = (Transaction) session.beginTransaction();
			retVal = (long) session.save(profile);
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
	public Boolean updateProfile(Profile profile) {
		Boolean isUpdated = false;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try{
			tx = (Transaction) session.beginTransaction();
			profile.getRecordTracker().setUpdatedAt(new Date());
			session.update(profile);
			tx.commit();
			isUpdated = true;
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.close();
		}
		
		return isUpdated;
	}

	@Override
	public Profile getProfile(String profileName) {
		Session session = sessionFactory.openSession();
		try {
			Query query = session.createQuery("from Profile where profileName=:profileName");
			query.setString("profileName", profileName);
			System.out.println("Get query string: " + query.getQueryString());
			Profile profile = (Profile) query.uniqueResult();
			return profile;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return null;

	}

	@Override
	public List<Profile> listProfiles() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Profile");
		List<Profile> profiles = new ArrayList<Profile>();
		profiles = query.list();
		session.close();
		return profiles;
	}

	@Override
	public Boolean deleteProfile(String profileName) {
		Boolean isDeleted = false;
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		
		try{
			tx = (Transaction) session.beginTransaction();
			Query query = session.createQuery("delete Profile where profileName=:profileName");
			query.setParameter("profileName", profileName);
			System.out.println("Delete query string: " + query.getQueryString());
			query.executeUpdate(); //returns # entity deleted or updated
			tx.commit();
			isDeleted = true;
		}catch(Exception e){
			if (tx!=null){
	    		tx.rollback();
	    	}
	    	e.printStackTrace(); 
		}finally{
			session.close();
		}
		
		return isDeleted;
	}

}
