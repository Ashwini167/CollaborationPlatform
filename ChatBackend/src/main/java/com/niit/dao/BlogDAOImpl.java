package com.niit.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.model.Blog;
import com.niit.model.BlogComment;

@Repository("blogDAO")
public class BlogDAOImpl implements BlogDAO {
	
	private static final Logger log = LoggerFactory.getLogger(BlogDAOImpl.class);
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public boolean addBlog(Blog blog) {
		try {
			log.info("Inside add Blog method -- try block");
			blog.setStatus("C");
			sessionFactory.getCurrentSession().save(blog);
			return true;
		}catch(Exception e) {
			log.error("Exception while adding a blog", e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteBlog(Blog blog) {
		try {
			log.info("Inside delete Blog method -- try block");
			blog.setStatus("D");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			log.error("Exception while deleting a blog", e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public boolean updateBlog(Blog blog) {
		try {
			log.info("Inside update Blog method -- try block");
			blog.setStatus("C");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			log.error("Exception while updating a blog", e);
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> blogListUser(String loginName) {
		try {
			log.info("Inside blog list retrieval for a specific user -- try block");
			String hql = "from Blog where loginName='"+loginName+"' and status!="+"'D'";
			return (List<Blog>)sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			log.error("Exception while blog list retrieval for a specific user", e);
			return null;
		}		
	}

	@Transactional
	@Override
	public boolean approveBlog(Blog blog) {
		try {
			log.info("Inside approve blog -- try block");
			blog.setStatus("A");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			log.error("Exception while approving a blog", e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean rejectBlog(Blog blog) {
		try {
			log.info("Inside reject blog -- try block");
			blog.setStatus("R");
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			log.error("Exception while rejecting a blog", e);
			return false;
		}
	}

	@Transactional
	@Override
	public Blog getBlog(int blogId) {
		try {
			log.info("Inside retrieve blog with blog Id method -- try block");
			return (Blog)sessionFactory.getCurrentSession().get(Blog.class,blogId);
		}catch(Exception e) {
			log.error("Exception while retrieving a blog with blog Id", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> blogList() {
		try {
			log.info("Inside retrieve all blogs (excluding deleted blogs) -- try block");
			String hql = "from Blog where status !="+"'D'";
			log.debug("Retrieve all blogs method: HQL is "+hql);
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			log.error("Exception while retrieving the entire blog list", e);
			return null;
		}
	}
	@Transactional
	@Override
	public boolean incrementLikes(Blog blog) {
		try {
			log.info("Inside increment likes for a blog method -- try block");
			int likes = blog.getLikes();
			likes++;
			blog.setLikes(likes);
			sessionFactory.getCurrentSession().update(blog);
			return true;
		}catch(Exception e) {
			log.error("Excetion in incrementing blog likes. ",e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getUnapprovedBlogs() {
		try {
			log.info("Inside retrieve unapproved blogs -- try block");
			String hql="from Blog where status='R'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			log.error("Exception while retrieving unapproved blog list", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getApprovedBlogs() {
		try {
			log.info("Inside retrieve approved blogs -- try block");
			String hql="from Blog where status='A'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		}catch(Exception e) {
			log.error("Exception while retrieving approved blog list", e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Blog> getToBeApprovedBlogs() {
		try {
			log.info("Inside retrieve to be approved blogs -- try block");
			String hql="from Blog where status='C'";
			return (List<Blog>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			log.error("Exception while retrieving to be approved blog list", e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public boolean addBlogComment(BlogComment blogComment) {
		try {
			log.info("Inside add blog comment method -- try block");
			blogComment.setStatus("P");
			sessionFactory.getCurrentSession().save(blogComment);
			return true;
		}catch(Exception e) {
			log.error("Exception while adding blog comment", e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteBlogComment(BlogComment blogComment) {
		try {
			log.info("Inside delete blog comment method -- try block");
			blogComment.setStatus("R");
			sessionFactory.getCurrentSession().update(blogComment);
			return true;
		}catch(Exception e) {
			log.error("Exception while deleting blog comment", e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean editBlogComment(BlogComment blogComment) {
		try {
			log.info("Inside edit blog comment method -- try block");
			sessionFactory.getCurrentSession().update(blogComment);
			return true;
		}catch(Exception e) {
			log.error("Exception while editing blog comment", e);
			return false;
		}
	}

	@Transactional
	@Override
	public BlogComment getComment(int commentId) {
		try {
			log.info("Inside retrieve blog comment based on commentId -- try block");
			return (BlogComment)sessionFactory.getCurrentSession().get(BlogComment.class,commentId);
		}catch(Exception e) {
			log.error("Exception while retrieving a specific blog comment using comment Id", e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<BlogComment> getBlogComments(int blogId) {
		try {
			log.info("Inside retrieve blog comments for a specific blog -- try block");
			String hql="from BlogComment where status="+"'P'"+" and blogId="+blogId;
			return (List<BlogComment>) sessionFactory.getCurrentSession().createQuery(hql).list();
		} catch(Exception e) {
			log.error("Exception while retrieving comments for a specific blog", e);
			return null;
		}
	}
}