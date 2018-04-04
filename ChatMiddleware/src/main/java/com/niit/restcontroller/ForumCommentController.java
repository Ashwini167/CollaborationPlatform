package com.niit.restcontroller;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.ForumDAO;
import com.niit.model.Forum;
import com.niit.model.ForumComment;
import com.niit.model.UserDetail;

@RestController
public class ForumCommentController {
	private static final Logger log = LoggerFactory.getLogger(ForumController.class);
	@Autowired
	ForumDAO forumDAO;
	
	@PostMapping("/addForumComment")
	public ResponseEntity<String> addForumComment(@RequestBody ForumComment forumComment) {
		forumComment.setForumCommentDate(new Date());
			
		Forum forum = new Forum();
		forum.setForumId(50150);
		forumComment.setForum(forum);
		
		//String loginName = session.getAttribute("loginName");
		String loginName = "Loga";
		log.debug("Login Name from session is "+loginName);
		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName(loginName);
		forumComment.setUserDetail(userDetail);
		
		log.debug("Add Forum Comment::: Comment text from form is: "+forumComment.getForumCommentText());
		
		if (forumDAO.addForumComment(forumComment)) {
			log.info("Adding forum comment by "+loginName+" was successful");
			return new ResponseEntity<String>("Forum comment Added successfully!",HttpStatus.OK);
		}
		else {
			log.info("Adding forum comment by "+loginName+" was not successful");
			return new ResponseEntity<String>("Error in adding the forum comment!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/editForumComment/{forumCommentId}")
	public ResponseEntity<String> editForumComment(@PathVariable("forumCommentId")int forumCommentId, @RequestBody ForumComment forumComment) {
		log.debug("Editing the comment with comment Id "+forumCommentId);
		
		log.debug("Update Forum Comment::: Comment text from form is: "+forumComment.getForumCommentText());
		
		if (forumDAO.editForumComment(forumComment)) {
			log.info("Updating forum comment with Id "+forumCommentId+" was successful");
			return new ResponseEntity<String>("Forum comment updated successfully!",HttpStatus.OK);
		}
		else {
			log.info("Updating forum comment with Id "+forumCommentId+" was not successful");
			return new ResponseEntity<String>("Error in updating the forum comment!",HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deleteForumComment/{forumCommentId}")
	public ResponseEntity<String> deleteForumComment(@PathVariable("forumCommentId")int forumCommentId) {
		log.debug("Deleting the comment with comment Id "+forumCommentId);
		ForumComment forumComment = forumDAO.getComment(forumCommentId);
		
		if (forumDAO.deleteForumComment(forumComment)) {
			log.info("Deleting forum comment with Id "+forumCommentId+" was successful");
			return new ResponseEntity<String>("Forum Added successfully!",HttpStatus.OK);
		}
		else {
			log.info("Deleting forum comment with Id "+forumCommentId+" was not successful");
			return new ResponseEntity<String>("Error in adding the forum!",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getComment/{forumCommentId}")
	public ResponseEntity<ForumComment> getComment(@PathVariable("forumCommentId")int forumCommentId) {
		ForumComment forumComment = forumDAO.getComment(forumCommentId);
		log.debug("Retrieving the comment with comment Id "+forumCommentId);
		
		if (forumComment!=null) {
			log.info("Retrieving forum comment with Id "+forumCommentId+" was successful");
			return new ResponseEntity<ForumComment>(forumComment,HttpStatus.OK);
		}
		else {
			log.info("Retrieving forum comment with Id "+forumCommentId+" was not successful");
			return new ResponseEntity<ForumComment>(forumComment,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getForumComments")
	public ResponseEntity<List<ForumComment>> getForumComments() {
		//int forumId = session.getAttribute("forumId");
		int forumId = 50150;
		List<ForumComment> forumCommentList = forumDAO.getForumComments(forumId);
		log.debug("Trying to retrieve comments for the forum "+forumId);
		
		if (forumCommentList!=null) {
			log.info("Retrieving comments for the forum "+forumId+" was successful");
			return new ResponseEntity<List<ForumComment>>(forumCommentList,HttpStatus.OK);
		}
		else {
			log.info("Retrieving comments for the forum "+forumId+" was not successful");
			return new ResponseEntity<List<ForumComment>>(forumCommentList,HttpStatus.NOT_FOUND);
		}
	}	
}