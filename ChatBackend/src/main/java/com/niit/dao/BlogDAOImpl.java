package com.niit.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.model.Blog;
import com.niit.model.BlogComment;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().save(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteBlog(Blog blog) {
		try {
			blog.setStatus("Disabled");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean updateBlog(Blog blog) {
		try {
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> blogListUser(String loginName) {
		try {
			String hql = "from Blog where loginName='"+loginName+"' and status!="+"'Disabled'";
			return (List<Blog>)sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}		
	}

	@Transactional
	@Override
	public boolean approveBlog(Blog blog) {
		try {
			blog.setStatus("Approved");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean rejectBlog(Blog blog) {
		try {
			blog.setStatus("Unapproved");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public Blog getBlog(int blogId) {
		try {
			return (Blog)sessionFactory.getCurrentSession().get(Blog.class,blogId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> blogList() {
		try {
			String hql = "from Blog where status !="+"'Disabled'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery("from Blog").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	@Transactional
	@Override
	public boolean incrementLikes(Blog blog) {
		try {
			int likes = blog.getLikes();
			likes++;
			blog.setLikes(likes);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getUnapprovedBlogs() {
		try {
			String hql="from Blog where status="+"'Unapproved'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getApprovedBlogs() {
		try {
			String hql="from Blog where status="+"'Approved'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getToBeApprovedBlogs() {
		try {
			String hql="from Blog where status="+"'create'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public boolean addBlogComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().save(blogComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteBlogComment(BlogComment blogComment) {
		try {
			blogComment.setStatus("removed");
			sessionFactory.getCurrentSession().update(blogComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean editBlogComment(BlogComment blogComment) {
		try {
			sessionFactory.getCurrentSession().update(blogComment);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public BlogComment getComment(int commentId) {
		try {
			return (BlogComment)sessionFactory.getCurrentSession().get(BlogComment.class,commentId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<BlogComment> getBlogComments(int blogId) {
		try {
			String hql="from BlogComment where status="+"'posted'"+" and blogId="+blogId;
			return (List<BlogComment>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
}