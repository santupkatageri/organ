package com.organocart.DAOImpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.organocart.DAO.Userservice;
import com.organocart.controller.HomeController;
import com.organocart.model.CartIdCred;
import com.organocart.model.UserRegCred;
import com.organocart.model.UserRegModel;

@Repository
public class UserServiceImpl implements Userservice {
	@Autowired
	SessionFactory sessionFactory;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public String acceptUser(UserRegCred u) {
		logger.info("Inside accept user method in userservice impl...");
		Session s = sessionFactory.openSession();
		Transaction t = s.getTransaction();
		t.begin();
		s.update(u);
		t.commit();
		s.close();
		logger.info("end of accept user method in userservice impl");
		return null;
	}

	@Override
	public String insertUser(UserRegModel u) {
		String status;
		try {
			logger.info("Inside insert user method in userservice impl");
			Session s = sessionFactory.openSession();
			Transaction t = s.getTransaction();
			t.begin();
			UserRegCred urc = new UserRegCred();
			urc.setEmailid(u.getEmailid());
			urc.setPassword(u.getPassword());
			
			CartIdCred cartcred = new CartIdCred();
			cartcred.setUserEmail(u.getEmailid());
			s.save(cartcred);
			s.save(u);
			s.save(urc);
			t.commit();
			s.close();
			status = "Success";
			logger.info("End of insert user method in userservice impl");
		} catch (Exception e) {
			logger.info("inside catch accept user method in userservice impl");
			status = "Fail";
		}
		return status;
	}

	@Override
	public String updateUser(UserRegModel u) {
		logger.info("Inside update user method in userservice impl");
		Session s = sessionFactory.openSession();
		Transaction t = s.getTransaction();
		t.begin();
		s.update(u);
		t.commit();
		s.close();
		logger.info("end of update user method in userservice impl");
		return null;
	}

	@Override
	public String deleteUser(UserRegModel u) {
		logger.info("Inside delete user method in userservice impl");
		Session s = sessionFactory.openSession();
		Transaction t = s.getTransaction();
		t.begin();
		s.delete(u);
		t.commit();
		s.close();
		logger.info("end of delete user method in userservice impl");
		return null;
	}

	@Override
	public String viewUsers(UserRegModel u) {
		
		logger.info("Inside view users method in userservice impl");
		Session s = sessionFactory.openSession();
		Transaction t = s.getTransaction();
		t.begin();
		List<UserRegModel> us = s.createQuery("from UserRegModel").list();
		//List<UserRegModel> users=s.get(UserRegModel.class);
		Gson g = new Gson();
		String s1 = g.toJson(us);
		t.commit();
		s.close();
		logger.info("end of view users method in userservice impl");
		return s1;
	}

	@Override
	public UserRegModel viewUser(String s) {
		logger.info("Inside view user method in userservice impl");
		Session sc = sessionFactory.openSession();
		Transaction t = sc.getTransaction();
		t.begin();
		UserRegModel us=(UserRegModel)sc.get(UserRegModel.class, s);
		t.commit();
		sc.close();
		logger.info("end of view user method in userservice impl");
		return us;
	}
}
