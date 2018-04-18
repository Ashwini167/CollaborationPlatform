package com.niit.dao;

import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.ProfilePicture;

@Repository("profilePictureDAO")
public class ProfilePictureDAOImpl implements ProfilePictureDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean savePicture(ProfilePicture profilePicture) {
		try {
			sessionFactory.getCurrentSession().save(profilePicture);
			return true;
		} catch(Exception e) {
			return false;
		}		
	}
	
	@Transactional
	@Override
	public ProfilePicture viewProfilePicture(String loginName) {
		try {
			return (ProfilePicture)sessionFactory.getCurrentSession().createQuery("from ProfilePicture").list().get(0);
		} catch(Exception e) {
			return null;
		}
	}
	
	@Transactional
	@Override
	public boolean updatePicture(ProfilePicture profilePicture) {
		try {
			sessionFactory.getCurrentSession().update(profilePicture);
			return true;
		} catch(Exception e) {
			return false;
		}
	}

}
