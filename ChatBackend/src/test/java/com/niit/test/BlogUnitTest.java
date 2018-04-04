package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.BlogDAO;
import com.niit.model.Blog;
import com.niit.model.BlogComment;
import com.niit.model.UserDetail;

public class BlogUnitTest {
	static BlogDAO blogDAO;
	
	@BeforeClass
	public static void executeFirst() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		blogDAO = (BlogDAO) context.getBean("blogDAO");
	}
	
	@Ignore
	@Test
	public void addBlogTest() {		
		Blog blog = new Blog();
		blog.setBlogName("Writing skills");
		blog.setBlogContent("Good writing skills allow you to communicate your message with clarity and ease to a far larger audience. Having good writing skills will help you hold a 'One-Up' position in the workplace.");
		blog.setCreatedDate(new Date());
		blog.setLikes(0);
		blog.setStatus("create");		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Anu");
		blog.setUserDetail(userDetail);
		
		assertTrue("Problem in adding the blog",blogDAO.addBlog(blog));
	}
	
	@Ignore
	@Test
	public void deleteBlogUnitTest(){
		Blog blog = blogDAO.getBlog(50100);
		assertTrue("Problem in deleting blog",blogDAO.deleteBlog(blog));
		
		System.out.println("Blog ID: "+blog.getBlogId());
		System.out.println("Blog Name: "+blog.getBlogName());
		System.out.println("Likes: "+blog.getLikes());
		System.out.println("Status: "+blog.getStatus());
	}
	
	@Ignore
	@Test
	public void updateBlogUnitTest(){ 
		Blog blog = blogDAO.getBlog(50100);
		blog.setStatus("Approved");
		assertTrue("Problem in updating the blog",blogDAO.updateBlog(blog));
		System.out.println("Blog ID: "+blog.getBlogId());
		System.out.println("Blog Name: "+blog.getBlogName());
		System.out.println("Likes: "+blog.getLikes());
		System.out.println("Status: "+blog.getStatus());
	}
	
	@Ignore
	@Test
	public void blogListUserUnitTest(){ 
		List<Blog> blogListForUser =  blogDAO.blogListUser("Anu");
		assertNotNull("Problem in retrieving the blog list using login name", blogListForUser);
		System.out.println("Blog ID \t Status \t Likes \tCreated Date \t\t Blog Name");
		for(Blog blog: blogListForUser) {
			System.out.print(blog.getBlogId()+"\t\t");
			System.out.print(blog.getStatus()+"\t\t");
			System.out.print(blog.getLikes()+"\t");
			System.out.print(blog.getCreatedDate()+"\t\t");
			System.out.println(blog.getBlogName());
		}		
	}
	
	@Ignore
	@Test
	public void approveBlogUnitTest(){ 
		Blog blog = blogDAO.getBlog(50400);
		assertTrue("Problem in updating the blog",blogDAO.approveBlog(blog));
		System.out.println("Blog ID: "+blog.getBlogId());
		System.out.println("Blog Name: "+blog.getBlogName());
		System.out.println("Likes: "+blog.getLikes());
		System.out.println("Status: "+blog.getStatus());
	}
	
	@Ignore
	@Test
	public void rejectBlogUnitTest(){ 
		Blog blog = blogDAO.getBlog(50350);
		assertTrue("Problem in updating the blog",blogDAO.rejectBlog(blog));
		System.out.println("Blog ID: "+blog.getBlogId());
		System.out.println("Blog Name: "+blog.getBlogName());
		System.out.println("Likes: "+blog.getLikes());
		System.out.println("Status: "+blog.getStatus());
	}
	
	@Ignore
	@Test
	public void getBlogUnitTest(){ 
		Blog blog = blogDAO.getBlog(50350);
		assertNotNull("Problem in retrieving the complete blog list",blog);
		System.out.println("Blog ID: "+blog.getBlogId());
		System.out.println("Blog Name: "+blog.getBlogName());
		System.out.println("Likes: "+blog.getLikes());
		System.out.println("Status: "+blog.getStatus());
	}
	
	@Ignore
	@Test
	public void incrementLikesUnitTest(){ 
		Blog blog = blogDAO.getBlog(50400);
		assertTrue("Problem while incrementing likes",blogDAO.incrementLikes(blog));
	}
	
	@Ignore
	@Test
	public void getUnapprovedBlogsUnitTest(){ 
		List<Blog> unapprovedBlogs =  blogDAO.getUnapprovedBlogs();
		assertNotNull("Problem in retrieving the blog list using login name", unapprovedBlogs);
		System.out.println("========================================================================================================");
		System.out.println("Unapproved blogs are as follows: ");
		System.out.println("Blog ID \t Status \t Likes \tCreated Date \t\t Blog Name");
		for(Blog blog: unapprovedBlogs) {
			System.out.print(blog.getBlogId()+"\t\t");
			System.out.print(blog.getStatus()+"\t\t");
			System.out.print(blog.getLikes()+"\t");
			System.out.print(blog.getCreatedDate()+"\t\t");
			System.out.println(blog.getBlogName());
		}
		System.out.println("========================================================================================================");		
	}
	
	@Ignore
	@Test
	public void getApprovedBlogsUnitTest(){ 
		List<Blog> approvedBlogs =  blogDAO.getApprovedBlogs();
		assertNotNull("Problem in retrieving the blog list using login name", approvedBlogs);
		System.out.println("========================================================================================================");
		System.out.println("Approved blogs are as follows: ");
		System.out.println("Blog ID \t Status \t Likes \tCreated Date \t\t Blog Name");
		for(Blog blog: approvedBlogs) {
			System.out.print(blog.getBlogId()+"\t\t");
			System.out.print(blog.getStatus()+"\t\t");
			System.out.print(blog.getLikes()+"\t");
			System.out.print(blog.getCreatedDate()+"\t\t");
			System.out.println(blog.getBlogName());
		}
		System.out.println("========================================================================================================");
	}
	
	@Ignore
	@Test
	public void getToBeApprovedBlogsUnitTest(){ 
		List<Blog> toBeApprovedBlogs =  blogDAO.getToBeApprovedBlogs();
		assertNotNull("Problem in retrieving the blog list using login name", toBeApprovedBlogs);
		System.out.println("========================================================================================================");
		System.out.println("To be approved blogs are as follows: ");
		System.out.println("Blog ID \t Status \t Likes \tCreated Date \t\t Blog Name");
		for(Blog blog: toBeApprovedBlogs) {
			System.out.print(blog.getBlogId()+"\t\t");
			System.out.print(blog.getStatus()+"\t\t");
			System.out.print(blog.getLikes()+"\t");
			System.out.print(blog.getCreatedDate()+"\t\t");
			System.out.println(blog.getBlogName());
		}
		System.out.println("========================================================================================================");
	}
	
	
	@Test
	public void blogListUnitTest(){ 
		List<Blog> blogList =  blogDAO.blogList();
		assertNotNull("Problem in retrieving the blog list using login name", blogList);
		System.out.println("Login name \t Blog ID \t Likes \t Created Date \t Status \t\t Blog Name");
		for(Blog blog: blogList) {
			System.out.print(blog.getUserDetail().getLoginName()+"\t");
			System.out.print(blog.getBlogId()+"\t\t");
			System.out.print(blog.getLikes()+"\t");
			System.out.print(blog.getCreatedDate()+"\t\t");
			System.out.print(blog.getStatus()+"\t\t");
			System.out.println(blog.getBlogName());
		}	
	}
	
	@Ignore
	@Test
	public void addBlogCommentUnitTest() {
		Blog blog = new Blog();
		blog.setBlogId(50400);
		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Admin");
		
		BlogComment comment = new BlogComment();
		comment.setCommentText("Meaningful post. Thanks for your contribution");
		comment.setCommentDate(new Date());
		comment.setBlog(blog);
		comment.setUserDetail(userDetail);
		
		assertTrue("Problem while adding comments to the blog",blogDAO.addBlogComment(comment));
	}
	
	@Ignore
	@Test
	public void deleteBlogCommentUnitTest() {
		BlogComment comment = blogDAO.getComment(50200);
		assertTrue("Problem in deleting the comment",blogDAO.deleteBlogComment(comment));
	}
	
	@Ignore
	@Test
	public void editBlogCommentUnitTest() {
		BlogComment comment = blogDAO.getComment(50100);
		comment.setCommentText("Very useful post!");
		assertTrue("Problem in deleting the comment",blogDAO.editBlogComment(comment));
	}
	
	@Ignore
	@Test
	public void getCommentUnitTest() {
		BlogComment comment = blogDAO.getComment(50200);
		assertNotNull("Problem in retrieving comment using commentId",comment);
	}
	@Ignore
	@Test
	public void getBlogCommentsUnitTest() {
		List<BlogComment> commentsList = blogDAO.getBlogComments(50400);
		assertNotNull("Problem in retrieving comments for this blog",commentsList);
		System.out.println("Blog ID \t Comment ID \t Comment Text \t \n=========================================================\n");
		for(BlogComment comment:commentsList) {
			System.out.print(comment.getBlog().getBlogId()+"\t");
			System.out.print(comment.getCommentId()+"\t");
			System.out.println(comment.getCommentText());
		}
	}	
}