package com.niit.restcontroller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.niit.dao.BlogDAO;
import com.niit.model.BlogComment;
import com.niit.model.UserDetail;

@RestController
public class BlogCommentController {
	private static final Gson gson = new Gson();
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);
	@Autowired
	BlogDAO blogDAO;
	
	@PostMapping("/addBlogComment")
	public ResponseEntity<String> addBlogComment(@RequestBody BlogComment blogComment, HttpSession session) {
		blogComment.setCommentDate(new Date());		
		String loginName = ((UserDetail)session.getAttribute("loggedInUser")).getLoginName();
		log.debug("Login Name from session is "+loginName);
		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName(loginName);
		blogComment.setUserDetail(userDetail);
		
		log.debug("Add Blog Comment::: Comment text from form is: "+blogComment.getCommentText());
		
		if (blogDAO.addBlogComment(blogComment)) {
			log.info("Adding blog comment by "+loginName+" was successful");
			return new ResponseEntity<String>(gson.toJson("Blog comment Added successfully!"),HttpStatus.OK);
		}
		else {
			log.info("Adding blog comment by "+loginName+" was not successful");
			return new ResponseEntity<String>(gson.toJson("Error in adding the blog comment!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/editBlogComment/{blogCommentId}")
	public ResponseEntity<String> editBlogComment(@PathVariable("blogCommentId")int blogCommentId, @RequestBody BlogComment blogComment) {
		log.debug("Editing the comment with comment Id "+blogCommentId);
		
		log.debug("Update Blog Comment::: Comment text from form is: "+blogComment.getCommentText());
		
		if (blogDAO.editBlogComment(blogComment)) {
			log.info("Updating blog comment with Id "+blogCommentId+" was successful");
			return new ResponseEntity<String>(gson.toJson("Blog comment updated successfully!"),HttpStatus.OK);
		}
		else {
			log.info("Updating blog comment with Id "+blogCommentId+" was not successful");
			return new ResponseEntity<String>(gson.toJson("Error in updating the blog comment!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deleteBlogComment/{blogCommentId}")
	public ResponseEntity<String> deleteBlogComment(@PathVariable("blogCommentId")int blogCommentId) {
		log.debug("Deleting the comment with comment Id "+blogCommentId);
		BlogComment blogComment = blogDAO.getComment(blogCommentId);
		
		if (blogDAO.deleteBlogComment(blogComment)) {
			log.info("Deleting blog comment with Id "+blogCommentId+" was successful");
			return new ResponseEntity<String>(gson.toJson("Comment deleted"),HttpStatus.OK);
		}
		else {
			log.info("Deleting blog comment with Id "+blogCommentId+" was not successful");
			return new ResponseEntity<String>(gson.toJson("Error while deleting the comment!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getComment/{blogCommentId}")
	public ResponseEntity<BlogComment> getComment(@PathVariable("blogCommentId")int blogCommentId) {
		BlogComment blogComment = blogDAO.getComment(blogCommentId);
		log.debug("Retrieving the comment with comment Id "+blogCommentId);
		
		if (blogComment!=null) {
			log.info("Retrieving blog comment with Id "+blogCommentId+" was successful");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
		else {
			log.info("Retrieving blog comment with Id "+blogCommentId+" was not successful");
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getBlogComments/{blogId}")
	public ResponseEntity<List<BlogComment>> getBlogComments(@PathVariable("blogId")int blogId) {
		List<BlogComment> blogCommentList = blogDAO.getBlogComments(blogId);
		log.debug("Trying to retrieve comments for the blog "+blogId);
		
		if (blogCommentList!=null) {
			log.info("Retrieving comments for the blog "+blogId+" was successful");
			return new ResponseEntity<List<BlogComment>>(blogCommentList,HttpStatus.OK);
		}
		else {
			log.info("Retrieving comments for the blog "+blogId+" was not successful");
			return new ResponseEntity<List<BlogComment>>(blogCommentList,HttpStatus.NOT_FOUND);
		}
	}	
}