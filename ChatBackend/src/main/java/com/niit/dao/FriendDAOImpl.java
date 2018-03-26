package com.niit.dao;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.model.Friend;

@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addFriendReq(Friend friend) {
		try {
			friend.setStatus("Requested");
			sessionFactory.getCurrentSession().save(friend);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean acceptFriend(Friend friend) {
		try {
			friend.setStatus("Accepted");
			friend.setRequestedOrAcceptedOn(new Date());
			sessionFactory.getCurrentSession().update(friend);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean rejectFriend(Friend friend) {
		try {
			friend.setStatus("Rejected");
			sessionFactory.getCurrentSession().update(friend);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Friend> viewFriendRequests(String loginName) {
		try {
			String hql="from Friend where status="+"'Requested'"+" and friend='"+loginName+"'";
			return (List<Friend>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Friend> viewFriendsList(String loginName) {
		try {
			String hql="from Friend where status="+"'Accepted'"+" and requestor='"+loginName+"'";
			return (List<Friend>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public Friend getFriendRequest(int friendId) {
		try {
			return (Friend)sessionFactory.getCurrentSession().get(Friend.class, friendId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
}