package com.niit.dao;

import java.util.List;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.niit.model.UserDetail;

@Repository("userDetailDAO")
public class UserDetailDAOImpl implements UserDetailDAO {
	private static final Logger log = LoggerFactory.getLogger(UserDetailDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addUserDetail(UserDetail userDetail) {
		try {
			log.info("Inside add user method - try block");
			userDetail.setEnabled("Yes");
			userDetail.setRole("ROLE_USER");
			sessionFactory.getCurrentSession().save(userDetail);
			return true;
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean deleteUserDetail(UserDetail userDetail) {
		try {
			log.info("Inside delete user method - try block");
			userDetail.setEnabled("FALSE");
			sessionFactory.getCurrentSession().update(userDetail);
			return true;
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean updateUserDetail(UserDetail userDetail) {
		try {
			log.info("Inside update user method - try block");
			sessionFactory.getCurrentSession().update(userDetail);
			return true;
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public UserDetail viewUserDetailByloginName(String loginName) {
		try {
			log.info("Inside fetch user detail by login name - try block");
			String hql = "from UserDetail where loginName='"+loginName+"'";
			return (UserDetail)sessionFactory.getCurrentSession().createQuery(hql).uniqueResult();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}		
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserDetail> listUserDetails() {
		try {
			log.info("Inside fetch user list - try block");
			return (List<UserDetail>)sessionFactory.getCurrentSession().createQuery("from UserDetail where enabled='Yes'").list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}	
	}
	
	@Transactional
	@Override
	public boolean authenticateUser(UserDetail userDetail) {
		try {
			log.info("Inside Authenticate user - try block");
			UserDetail userFromDB = (UserDetail)sessionFactory.getCurrentSession().createQuery("from UserDetail where loginName='"+userDetail.getLoginName()+"' and password='"+userDetail.getPassword()+"'").list().get(0);
			log.debug("User details from form: \n Login name: "+userDetail.getLoginName()+"/n Password: "+userDetail.getPassword());
			if(userFromDB!=null) {
				log.debug("User from DB is: "+userFromDB.getLoginName());
				return true;
			} else {
				log.debug("User from DB is null");
				return false;
			}
		}catch(Exception e) {
			log.error("Exception in validating user credentials"+e);
			return false;
		}
	}	
}