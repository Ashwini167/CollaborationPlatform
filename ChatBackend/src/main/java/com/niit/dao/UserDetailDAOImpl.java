package com.niit.dao;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.niit.model.UserDetail;

@Repository("userDetailDAO")
public class UserDetailDAOImpl implements UserDetailDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addUserDetail(UserDetail userDetail) {
		try {
			sessionFactory.getCurrentSession().save(userDetail);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean deleteUserDetail(UserDetail userDetail) {
		try {
			sessionFactory.getCurrentSession().delete(userDetail);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}
	@Transactional
	@Override
	public boolean updateUserDetail(UserDetail userDetail) {
		try {
			sessionFactory.getCurrentSession().update(userDetail);
			return true;
		}
		catch(Exception e) {
			System.out.println("There is an exception here! The details are: \n =================================");
			System.out.println(e);
			return false;
		}
	}

	@Override
	public UserDetail viewUserDetailByEmail(String emailId) {
		Session session=sessionFactory.openSession();
		//UserDetail userDetail=(UserDetail)session.get(UserDetail.class,emailId);
		Query query = session.createQuery("from UserDetail where emailID='"+emailId+"'");
		UserDetail userDetail=(UserDetail)query.uniqueResult();
		session.close();
		return userDetail;
	}

	@Override
	public UserDetail viewUserDetailByloginName(String loginName) {
		Session session=sessionFactory.openSession();
		Query query = session.createQuery("from UserDetail where loginName='"+loginName+"'");
		UserDetail userDetail=(UserDetail)query.uniqueResult();
		session.close();
		return userDetail;
	}

	@Override
	public List<UserDetail> listUserDetails() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from UserDetail");
		List<UserDetail> listUserDetails = (List<UserDetail>)query.list();
		return listUserDetails;		
	}	
}