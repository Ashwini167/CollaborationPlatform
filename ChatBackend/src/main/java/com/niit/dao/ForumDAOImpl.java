package com.niit.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Forum;
import com.niit.model.ForumComment;

@Repository("forumDAO")
public class ForumDAOImpl implements ForumDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addForum(Forum forum) {
		try {
			sessionFactory.getCurrentSession().save(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean deleteForum(Forum forum) {
		try {
			forum.setStatus("Disabled");
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	

	@Transactional
	@Override
	public boolean updateForum(Forum forum) {
		try {
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Forum> forumListUser(String loginName) {
		try {
			String hql = "from Forum where loginName='"+loginName+"' and status!="+"'Disabled'";
			return (List<Forum>)sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}		
	}

	@Transactional
	@Override
	public boolean approveForum(Forum forum) {
		try {
			forum.setStatus("Approved");
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean rejectForum(Forum forum) {
		try {
			forum.setStatus("Unapproved");
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public Forum getForum(int forumId) {
		try {
			return (Forum)sessionFactory.getCurrentSession().get(Forum.class,forumId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Forum> forumList() {
		try {
			String hql = "from Forum where status !="+"'Disabled'";
			return (List<Forum>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	@Transactional
	@Override
	public boolean incrementLikes(Forum forum) {
		try {
			int likes = forum.getLikes();
			likes++;
			forum.setLikes(likes);
			sessionFactory.getCurrentSession().update(forum);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Forum> getUnapprovedForums() {
		try {
			String hql="from Forum where status="+"'Unapproved'";
			return (List<Forum>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Forum> getApprovedForums() {
		try {
			String hql="from Forum where status="+"'Approved'";
			return (List<Forum>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Forum> getToBeApprovedForums() {
		try {
			String hql="from Forum where status="+"'create'";
			return (List<Forum>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public boolean addForumComment(ForumComment forumComment) {
		try {
			forumComment.setStatus("posted");
			sessionFactory.getCurrentSession().save(forumComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteForumComment(ForumComment forumComment) {
		try {
			forumComment.setStatus("removed");
			sessionFactory.getCurrentSession().update(forumComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean editForumComment(ForumComment forumComment) {
		try {
			sessionFactory.getCurrentSession().update(forumComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public ForumComment getComment(int commentId) {
		try {
			return (ForumComment)sessionFactory.getCurrentSession().get(ForumComment.class,commentId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ForumComment> getForumComments(int forumId) {
		try {
			String hql="from ForumComment where status="+"'posted'"+" and forumId="+forumId;
			return (List<ForumComment>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
}