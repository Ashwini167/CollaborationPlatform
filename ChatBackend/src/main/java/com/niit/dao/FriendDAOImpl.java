package com.niit.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.model.Friend;
import com.niit.model.UserDetail;

@Repository("friendDAO")
public class FriendDAOImpl implements FriendDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addFriendReq(Friend friend) {
		try {
			friend.setStatus("C");
			friend.setIsOnline("N");
			friend.setRequestedOrAcceptedOn(new Date());
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
			friend.setStatus("A");
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
			friend.setStatus("R");
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
	public Map<Integer,UserDetail> viewFriendRequests(String loginName) {
		try {
			String hql = "select friendId, requestor from friend where status='C' AND tofriend='"+loginName+"'";
			List<Object[]> objList = (List<Object[]>)sessionFactory.getCurrentSession().createSQLQuery(hql).list();	
			Map<Integer,UserDetail> pendingFriends = new HashMap<Integer,UserDetail>();
			for(Object[] obj:objList) {
				pendingFriends.put(Integer.parseInt(obj[0].toString()), (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, obj[1].toString()));
			}
			return pendingFriends;
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
			String hql= "from Friend where status='A' AND (requestor='"+loginName+"' OR friend='"+loginName+"')";
			return sessionFactory.getCurrentSession().createQuery(hql).list();
			//List<Object[]> objList = (List<Object[]>)sessionFactory.getCurrentSession().createSQLQuery(hql).list();	
			//Map<Integer,UserDetail> friendsList = new HashMap<Integer,UserDetail>();
			//for(Object[] obj:objList) {
				//friendsList.put(Integer.parseInt(obj[0].toString()), (UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, obj[1].toString()));
			//}
			//return friendsList;
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<UserDetail> viewSuggestedFriends(String loginName) {
		try {
			String hql="select loginName from userDetail where loginName not in (select loginName from toFriend where friendId in (select friendId from requestor where loginName='"+loginName+"')) AND loginName!='"+loginName+"'";
			List<Object> objList = (List<Object>)sessionFactory.getCurrentSession().createSQLQuery(hql).list();	
			Iterator<Object> itr = objList.iterator();
			List<UserDetail> suggestedFriends = new ArrayList<UserDetail>();
			while(itr.hasNext()) {
				suggestedFriends.add((UserDetail)sessionFactory.getCurrentSession().get(UserDetail.class, itr.next().toString()));
			}
			return suggestedFriends;
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
}