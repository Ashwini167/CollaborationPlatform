package com.niit.restcontroller;

import java.util.List;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.niit.model.Forum;
import com.niit.model.UserDetail;
import com.niit.dao.ForumDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ForumController {
	private static final Logger log = LoggerFactory.getLogger(ForumController.class);
	@Autowired
	ForumDAO forumDAO;
	
	@GetMapping("/showAllForumsOfUser")
	public ResponseEntity<List<Forum>> showAllForumsOfUser() {
		log.info("Inside showAllForums of a specific user");
		//String loginName = session.getAttribute("loginName");
		String loginName = "Anu";
		log.debug("Login Name from session is "+loginName);
		List<Forum> listUsersForum = forumDAO.forumListUser(loginName);
		if(listUsersForum!=null) {
			log.debug("User "+loginName+" has atleast one forum written");
			return new ResponseEntity<List<Forum>>(listUsersForum,HttpStatus.OK);
		}
		else {
			log.debug("User "+loginName+" has no forums written");
			return new ResponseEntity<List<Forum>>(listUsersForum,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@PostMapping("/addForum")
	public ResponseEntity<String> addForum(@RequestBody Forum forum){		
		
		//String loginName = session.getAttribute("loginName");
		String loginName = "Anu";
		log.debug("Login Name from session is "+loginName);
		forum.setCreatedDate(new Date());
		forum.setLikes(0);
		forum.setStatus("create");
		
		UserDetail user = new UserDetail();
		user.setLoginName(loginName);
		forum.setUserDetail(user);
		
		log.debug("Add Forum::: Forum Name from form is: "+forum.getForumName());
		log.debug("Add Forum::: Forum Content from form is: \n"+forum.getForumContent());
		
		if (forumDAO.addForum(forum)) {
			log.info("Adding forum by "+loginName+" was successful");
			return new ResponseEntity<String>("Forum Added successfully!",HttpStatus.OK);
		}
		else {
			log.info("Adding forum by "+loginName+" was not successful");
			return new ResponseEntity<String>("Error in adding the forum!",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/approveForum/{forumId}")
	public ResponseEntity<String> approveForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		log.info("Inside approve forum method for forum Id "+forumId);
		
		if (forumDAO.approveForum(forum)) {
			log.info("Approving "+forumId+" was successful");
			return new ResponseEntity<String>("Forum Approved!",HttpStatus.OK);
		}
		else {
			log.info("Approving "+forumId+" was not successful");
			return new ResponseEntity<String>("Error in approving the forum!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/rejectForum/{forumId}")
	public ResponseEntity<String> rejectForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		log.info("Inside approve forum method for forum Id "+forumId);		
		if (forumDAO.rejectForum(forum)) {
			log.info("Rejecting "+forumId+" was successful");
			return new ResponseEntity<String>("Forum Rejected!",HttpStatus.OK);
		}
		else {
			log.info("Rejecting "+forumId+" was not successful");
			return new ResponseEntity<String>("Error in rejecting the forum!",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/deleteForum/{forumId}")
	public ResponseEntity<String> deleteForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		log.info("Inside delete forum method for forum Id "+forumId);
		
		if (forumDAO.deleteForum(forum)) {
			log.info("Deleting "+forumId+" was successful");
			return new ResponseEntity<String>("Forum deleted!",HttpStatus.OK);
		}
		else {
			log.info("Deleting "+forumId+" was not successful");
			return new ResponseEntity<String>("Error in deleting the forum!",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/incrementLikes/{forumId}")
	public ResponseEntity<String> incrementLikes(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		log.info("Inside increment likes for forum "+forumId);
		
		if (forumDAO.incrementLikes(forum)) {
			log.info("Incrementing likes for "+forumId+" was successful");
			return new ResponseEntity<String>("Forum liked!",HttpStatus.OK);
		}
		else {
			log.info("Incrementing likes for "+forumId+" was not successful");
			return new ResponseEntity<String>("Error in liking the forum!",HttpStatus.NOT_FOUND);
		}
	} 
	
	@PutMapping("/updateForum/{forumId}")
	public ResponseEntity<String> updateForum(@PathVariable("forumId") int forumId,@RequestBody Forum forum) {
		log.info("Inside update forumId "+forumId+" method");
		log.debug("Update Forum::: Forum Name from form is: "+forum.getForumName());
		log.debug("Update Forum::: Forum Content from form is: \n"+forum.getForumContent());		
		
		if (forumDAO.updateForum(forum)) {
			log.info("Updating "+forumId+" was successful");
			return new ResponseEntity<String>("Forum updated!",HttpStatus.OK);
		}
		else {
			log.info("Updating "+forumId+" was successful");
			return new ResponseEntity<String>("Error in updating the forum!",HttpStatus.NOT_FOUND);
		}
	} 
	
	@GetMapping("/getForum/{forumId}")
	public ResponseEntity<Forum> getForum(@PathVariable("forumId") int forumId) {
		Forum forum = forumDAO.getForum(forumId);
		log.info("Inside retrieve forumId "+forumId+" method");
		if(forum!=null) {
			log.info("Retrieving forum details with "+forumId+" was successful");
			return new ResponseEntity<Forum>(forum,HttpStatus.OK);
		}
		else {
			log.info("Retrieving forum details with "+forumId+" was not successful");
			return new ResponseEntity<Forum>(forum,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showAllForums")
	public ResponseEntity<List<Forum>> showAllForums() {
		log.info("Inside showAllForums of all users");
		List<Forum> forumList = forumDAO.forumList();
		if(forumList!=null) {
			log.info("Retrieving entire forum list was successful");
			return new ResponseEntity<List<Forum>>(forumList,HttpStatus.OK);
		}
		else {
			log.info("Retrieving entire forum list was not successful");
			return new ResponseEntity<List<Forum>>(forumList,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showApprovedForums")
	public ResponseEntity<List<Forum>> showApprovedForums() {
		log.info("Inside show approved forums of all users");
		List<Forum> approvedForums = forumDAO.getApprovedForums();
		if(approvedForums!=null) {
			log.info("Retrieving approved forum list was successful");
			return new ResponseEntity<List<Forum>>(approvedForums,HttpStatus.OK);
		}
		else {
			log.info("Retrieving approved forum list was not successful");
			return new ResponseEntity<List<Forum>>(approvedForums,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showUnapprovedForums")
	public ResponseEntity<List<Forum>> showUnapprovedForums() {
		log.info("Inside show unapproved forums of all users");
		List<Forum> rejectedForums = forumDAO.getUnapprovedForums();
		if(rejectedForums!=null) {
			log.info("Retrieving unapproved forum list was successful");
			return new ResponseEntity<List<Forum>>(rejectedForums,HttpStatus.OK);
		}
		else {
			log.info("Retrieving unapproved forum list was not successful");
			return new ResponseEntity<List<Forum>>(rejectedForums,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
	
	@GetMapping("/showToBeApprovedForums")
	public ResponseEntity<List<Forum>> showToBeApprovedForums() {
		log.info("Inside show to be approved forums of all users");
		List<Forum> toBeApproved = forumDAO.getToBeApprovedForums();
		if(toBeApproved!=null) {
			log.info("Retrieving to be approved forum list was successful");
			return new ResponseEntity<List<Forum>>(toBeApproved,HttpStatus.OK);
		}
		else {
			log.info("Retrieving to be approved forum list was not successful");
			return new ResponseEntity<List<Forum>>(toBeApproved,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}
