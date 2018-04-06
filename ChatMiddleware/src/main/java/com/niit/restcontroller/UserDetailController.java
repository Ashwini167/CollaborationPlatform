package com.niit.restcontroller;

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

import com.google.gson.Gson;
import com.niit.dao.UserDetailDAO;
import com.niit.model.UserDetail;

@RestController
public class UserDetailController {
	private static final Gson gson = new Gson();
	private static final Logger log = LoggerFactory.getLogger(UserDetailController.class);
	
	@Autowired
	UserDetailDAO userDetailDAO;
	
	@PostMapping("/addUserDetails")
	public ResponseEntity<String> addUserDetails(@RequestBody UserDetail userDetail) {
		
		log.debug("Login name from form: "+userDetail.getLoginName());
		log.debug("Password from form: "+userDetail.getPassword());
		log.debug("Mobile Number from form: "+userDetail.getMobileNo());
		log.debug("EmailId from form: "+userDetail.getEmailId());
		log.debug("Address from form: "+userDetail.getAddress());
		log.debug("Name of the user (from form): "+userDetail.getUsername());
		
		if(userDetailDAO.addUserDetail(userDetail)) {
			log.info("Adding new user is successful");
			return new ResponseEntity<String>(gson.toJson("Adding new user is successful!"),HttpStatus.OK);
		}else {
			log.info("Adding new user was not successful");
			return new ResponseEntity<String>(gson.toJson("Error in adding new user!"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/editUserProfile/{loginName}")
	public ResponseEntity<String> editUserProfile(@PathVariable("loginName") String loginName, @RequestBody UserDetail userDetail){
		if(userDetailDAO.updateUserDetail(userDetail)) {
			log.info("Updating user details is successful");
			return new ResponseEntity<String>(gson.toJson("Updating user details is successful!"),HttpStatus.OK);
		}else {
			log.info("Updating user details was not successful");
			return new ResponseEntity<String>(gson.toJson("Error in updating user!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deleteUser/{loginName}")
	public ResponseEntity<String> removeUserProfile(@PathVariable("loginName") String loginName){
		UserDetail userDetail = userDetailDAO.viewUserDetailByloginName(loginName);
		if(userDetailDAO.deleteUserDetail(userDetail)) {
			log.info("Removing user profile is successful");
			return new ResponseEntity<String>(gson.toJson("Removing user profile is successful!"),HttpStatus.OK);
		}else {
			log.info("Removing user profile was not successful");
			return new ResponseEntity<String>(gson.toJson("Error in deleting user!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewUserDetail/{loginName}")
	public ResponseEntity<UserDetail> viewUserDetails(@PathVariable("loginName") String loginName) {
		UserDetail userDetail = userDetailDAO.viewUserDetailByloginName(loginName);
		if(userDetail!=null) {
			log.info("Retrieving user profile with loginName"+loginName+" is successful");
			return new ResponseEntity<UserDetail>(userDetail,HttpStatus.OK);
		}else {
			log.info("Retrieving user profile with loginName"+loginName+" was not successful");
			return new ResponseEntity<UserDetail>(userDetail,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listUserDetails")
	public ResponseEntity<List<UserDetail>> listUserDetails(){
		List<UserDetail> userList = userDetailDAO.listUserDetails();
		if(userList!=null) {
			log.info("Retrieving all the users' profile is successful");
			return new ResponseEntity<List<UserDetail>>(userList,HttpStatus.OK);
		}else {
			log.info("Retrieving all the users' profile was not successful");
			return new ResponseEntity<List<UserDetail>>(userList,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/authenticateUser")
	public ResponseEntity<UserDetail> authenticateUser(@RequestBody UserDetail userDetail){
		UserDetail loggedInUser = null;
		if(userDetailDAO.authenticateUser(userDetail)) {
			loggedInUser = userDetailDAO.viewUserDetailByloginName(userDetail.getLoginName());
			log.info("User Authentication successful");
			return new ResponseEntity<UserDetail>(loggedInUser,HttpStatus.OK);
		}else {
			log.info("User Authentication failed");
			return new ResponseEntity<UserDetail>(loggedInUser,HttpStatus.UNAUTHORIZED);
		}
	}
}
