package com.niit.dao;

import java.util.List;
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
			userDetail.setEnabled("No");
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
			sessionFactory.getCurrentSession().update(userDetail);
			return true;
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	/*@Override
	public UserDetail viewUserDetailByEmail(String emailId) {
		Session session=sessionFactory.openSession();
		//UserDetail userDetail=(UserDetail)session.get(UserDetail.class,emailId);
		Query query = session.createQuery("from UserDetail where emailID='"+emailId+"'");
		UserDetail userDetail=(UserDetail)query.uniqueResult();
		session.close();
		return userDetail;
	}*/

	@Transactional
	@Override
	public UserDetail viewUserDetailByloginName(String loginName) {
		try {
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
			return (List<UserDetail>)sessionFactory.getCurrentSession().createQuery("from UserDetail where enabled='Yes'").list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}	
	}	
}