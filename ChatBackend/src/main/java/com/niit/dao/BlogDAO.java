package com.niit.dao;

import java.util.List;

import com.niit.model.Blog;
import com.niit.model.BlogComment;

public interface BlogDAO {
	public boolean addBlog(Blog blog);
	public boolean deleteBlog(Blog blog);
	public boolean updateBlog(Blog blog);
	public List<Blog> blogListUser(String loginName);
	public boolean approveBlog(Blog blog);
	public boolean rejectBlog(Blog blog);
	public Blog getBlog(int blogId);
	public List<Blog> blogList();
	public boolean incrementLikes(Blog blog);
	public List<Blog> getUnapprovedBlogs();
	public List<Blog> getApprovedBlogs();
	public List<Blog> getToBeApprovedBlogs();
	
	public boolean addBlogComment(BlogComment blogComment);
	public boolean deleteBlogComment(BlogComment blogComment);
	public boolean editBlogComment(BlogComment blogComment);
	public BlogComment getComment(int commentId);
	public List<BlogComment> getBlogComments(int blogId);	
}
