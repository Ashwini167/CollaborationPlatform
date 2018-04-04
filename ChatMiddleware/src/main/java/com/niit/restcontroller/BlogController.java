package com.niit.restcontroller;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.BlogDAO;
import com.niit.model.Blog;
import com.niit.model.UserDetail;

@RestController
public class BlogController {	
	
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);
	@Autowired
	BlogDAO blogDAO;
	
	@GetMapping("/showAllBlogsOfUser")
	public ResponseEntity<List<Blog>> showAllBlogsOfUser() {
		log.info("Inside showAllBlogs of a specific user");
		//String loginName = session.getAttribute("loginName");
		String loginName = "Anu";
		log.debug("Login Name from session is "+loginName);
		List<Blog> listUsersBlog = blogDAO.blogListUser(loginName);
		if(listUsersBlog!=null) {
			log.debug("User "+loginName+" has atleast one blog written");
			return new ResponseEntity<List<Blog>>(listUsersBlog,HttpStatus.OK);
		}
		else {
			log.debug("User "+loginName+" has no blogs written");
			return new ResponseEntity<List<Blog>>(listUsersBlog,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping("/addBlog")
	public ResponseEntity<String> addBlog(@RequestBody Blog blog){		
		
		//String loginName = session.getAttribute("loginName");
		String loginName = "Anu";
		log.debug("Login Name from session is "+loginName);
		blog.setCreatedDate(new Date());
		blog.setLikes(0);
		blog.setStatus("create");
		
		UserDetail user = new UserDetail();
		user.setLoginName(loginName);
		blog.setUserDetail(user);
		
		log.debug("Add Blog::: Blog Name from form is: "+blog.getBlogName());
		log.debug("Add Blog::: Blog Content from form is: \n"+blog.getBlogContent());
		
		if (blogDAO.addBlog(blog)) {
			log.info("Adding blog by "+loginName+" was successful");
			return new ResponseEntity<String>("Blog Added successfully!",HttpStatus.OK);
		}
		else {
			log.info("Adding blog by "+loginName+" was not successful");
			return new ResponseEntity<String>("Error in adding the blog!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/approveBlog/{blogId}")
	public ResponseEntity<String> approveBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		log.info("Inside approve blog method for blog Id "+blogId);
		
		if (blogDAO.approveBlog(blog)) {
			log.info("Approving "+blogId+" was successful");
			return new ResponseEntity<String>("Blog Approved!",HttpStatus.OK);
		}
		else {
			log.info("Approving "+blogId+" was not successful");
			return new ResponseEntity<String>("Error in approving the blog!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/rejectBlog/{blogId}")
	public ResponseEntity<String> rejectBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		log.info("Inside approve blog method for blog Id "+blogId);		
		if (blogDAO.rejectBlog(blog)) {
			log.info("Rejecting "+blogId+" was successful");
			return new ResponseEntity<String>("Blog Rejected!",HttpStatus.OK);
		}
		else {
			log.info("Rejecting "+blogId+" was not successful");
			return new ResponseEntity<String>("Error in rejecting the blog!",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/deleteBlog/{blogId}")
	public ResponseEntity<String> deleteBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		log.info("Inside delete blog method for blog Id "+blogId);
		
		if (blogDAO.deleteBlog(blog)) {
			log.info("Deleting "+blogId+" was successful");
			return new ResponseEntity<String>("Blog deleted!",HttpStatus.OK);
		}
		else {
			log.info("Deleting "+blogId+" was not successful");
			return new ResponseEntity<String>("Error in deleting the blog!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/incrementLikes/{blogId}")
	public ResponseEntity<String> incrementLikes(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		log.info("Inside increment likes for blog "+blogId);
		
		if (blogDAO.incrementLikes(blog)) {
			log.info("Incrementing likes for "+blogId+" was successful");
			return new ResponseEntity<String>("Blog liked!",HttpStatus.OK);
		}
		else {
			log.info("Incrementing likes for "+blogId+" was not successful");
			return new ResponseEntity<String>("Error in liking the blog!",HttpStatus.NOT_FOUND);
		}
	} 
	
	@PutMapping("/updateBlog/{blogId}")
	public ResponseEntity<String> updateBlog(@PathVariable("blogId") int blogId,@RequestBody Blog blog) {
		log.info("Inside update blogId "+blogId+" method");
		log.debug("Update Blog::: Blog Name from form is: "+blog.getBlogName());
		log.debug("Update Blog::: Blog Content from form is: \n"+blog.getBlogContent());		
		
		if (blogDAO.updateBlog(blog)) {
			log.info("Updating "+blogId+" was successful");
			return new ResponseEntity<String>("Blog updated!",HttpStatus.OK);
		}
		else {
			log.info("Updating "+blogId+" was successful");
			return new ResponseEntity<String>("Error in updating the blog!",HttpStatus.NOT_FOUND);
		}
	} 
	
	@GetMapping("/getBlog/{blogId}")
	public ResponseEntity<Blog> getBlog(@PathVariable("blogId") int blogId) {
		Blog blog = blogDAO.getBlog(blogId);
		log.info("Inside retrieve blogId "+blogId+" method");
		if(blog!=null) {
			log.info("Retrieving blog details with "+blogId+" was successful");
			return new ResponseEntity<Blog>(blog,HttpStatus.OK);
		}
		else {
			log.info("Retrieving blog details with "+blogId+" was not successful");
			return new ResponseEntity<Blog>(blog,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showAllBlogs")
	public ResponseEntity<List<Blog>> showAllBlogs() {
		log.info("Inside showAllBlogs of all users");
		List<Blog> blogList = blogDAO.blogList();
		if(blogList!=null) {
			log.info("Retrieving entire blog list was successful");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.OK);
		}
		else {
			log.info("Retrieving entire blog list was not successful");
			return new ResponseEntity<List<Blog>>(blogList,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showApprovedBlogs")
	public ResponseEntity<List<Blog>> showApprovedBlogs() {
		log.info("Inside show approved blogs of all users");
		List<Blog> approvedBlogs = blogDAO.getApprovedBlogs();
		if(approvedBlogs!=null) {
			log.info("Retrieving approved blog list was successful");
			return new ResponseEntity<List<Blog>>(approvedBlogs,HttpStatus.OK);
		}
		else {
			log.info("Retrieving approved blog list was not successful");
			return new ResponseEntity<List<Blog>>(approvedBlogs,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showUnapprovedBlogs")
	public ResponseEntity<List<Blog>> showUnapprovedBlogs() {
		log.info("Inside show unapproved blogs of all users");
		List<Blog> rejectedBlogs = blogDAO.getUnapprovedBlogs();
		if(rejectedBlogs!=null) {
			log.info("Retrieving unapproved blog list was successful");
			return new ResponseEntity<List<Blog>>(rejectedBlogs,HttpStatus.OK);
		}
		else {
			log.info("Retrieving unapproved blog list was not successful");
			return new ResponseEntity<List<Blog>>(rejectedBlogs,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showToBeApprovedBlogs")
	public ResponseEntity<List<Blog>> showToBeApprovedBlogs() {
		log.info("Inside show to be approved blogs of all users");
		List<Blog> toBeApproved = blogDAO.getToBeApprovedBlogs();
		if(toBeApproved!=null) {
			log.info("Retrieving to be approved blog list was successful");
			return new ResponseEntity<List<Blog>>(toBeApproved,HttpStatus.OK);
		}
		else {
			log.info("Retrieving to be approved blog list was not successful");
			return new ResponseEntity<List<Blog>>(toBeApproved,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}