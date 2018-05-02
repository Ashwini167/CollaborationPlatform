package com.niit.restcontroller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.niit.dao.FriendDAO;
import com.niit.model.Friend;
import com.niit.model.UserDetail;

@RestController
public class FriendController {
	private static final Gson gson = new Gson();
	private static final Logger log = LoggerFactory.getLogger(FriendController.class);
	
	@Autowired
	FriendDAO friendDAO;
	
	@GetMapping("/sendFriendRequest/{loginName}")
	public ResponseEntity<String> sendFriendRequest(@PathVariable("loginName")String friend, HttpSession session){
		UserDetail requestor = (UserDetail)session.getAttribute("loggedInUser");
		UserDetail toFriend = new UserDetail();
		toFriend.setLoginName(friend);
		
		Friend frnd = new Friend();
		frnd.setRequestor(requestor);
		frnd.setFriend(toFriend);
		
		if(friendDAO.addFriendReq(frnd)) {
			log.info("Friend request from "+requestor.getLoginName()+" to "+friend+" was successful");
			return new ResponseEntity<String>(gson.toJson("Friend Request sent!"),HttpStatus.OK);
		} else {
			log.info("Friend request from "+requestor.getLoginName()+" to "+friend+" was not successful");
			return new ResponseEntity<String>(gson.toJson("Problem in adding friend"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/showSuggestedFriends")
	public ResponseEntity<List<UserDetail>> showSuggestedFriends(HttpSession session){
		String loginName = ((UserDetail)session.getAttribute("loggedInUser")).getLoginName();
		List<UserDetail> suggestedFriendList = friendDAO.viewSuggestedFriends(loginName);
		if(suggestedFriendList!=null) {
			log.info("Friend suggestions for "+loginName+" was fetched succesfully");
			return new ResponseEntity<List<UserDetail>>(suggestedFriendList,HttpStatus.OK);
		} else {
			log.info("Friend suggestions for "+loginName+" was not fetched succesfully");
			return new ResponseEntity<List<UserDetail>>(suggestedFriendList,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewPendingRequests")
	public ResponseEntity<Map<Integer,UserDetail>> showPendingFriendRequests(HttpSession session){
		String loginName = ((UserDetail)session.getAttribute("loggedInUser")).getLoginName();
		Map<Integer,UserDetail> pendingFriendList = friendDAO.viewFriendRequests(loginName);
		if(pendingFriendList!=null) {
			log.info("Friend requests for "+loginName+" was fetched succesfully");
			return new ResponseEntity<Map<Integer,UserDetail>>(pendingFriendList,HttpStatus.OK);
		} else {
			log.info("Friend requests for "+loginName+" was not fetched succesfully");
			return new ResponseEntity<Map<Integer,UserDetail>>(pendingFriendList,HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/acceptFriend")
	public ResponseEntity<String> acceptFriend(@RequestBody int friendId, HttpSession session){
		Friend friend = friendDAO.getFriendRequest(friendId);
		if(friendId>0 && friendDAO.acceptFriend(friend)) {
			log.info("Friend request accepted succesfully");
			return new ResponseEntity<String>(gson.toJson("Friend Request Accepted!"),HttpStatus.OK);
		} else {
			log.info("Friend request is not accepted");
			return new ResponseEntity<String>(gson.toJson("Friend Request Accepted!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/rejectFriend")
	public ResponseEntity<String> rejectFriend(@RequestBody int friendId, HttpSession session){
		Friend friend = friendDAO.getFriendRequest(friendId);
		if(friendId>0 && friendDAO.rejectFriend(friend)) {
			log.info("Friend request rejected succesfully");
			return new ResponseEntity<String>(gson.toJson("Friend Request rejected!"),HttpStatus.OK);
		} else {
			log.info("Friend request is not rejected");
			return new ResponseEntity<String>(gson.toJson("Friend Request rejected!"),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewFriends")
	public ResponseEntity<List<Friend>> viewFriends(HttpSession session){
		String loginName = ((UserDetail)session.getAttribute("loggedInUser")).getLoginName();
		List<Friend> friendsList = friendDAO.viewFriendsList(loginName);
		if(friendsList!=null) {
			log.info("Friend list for "+loginName+" was fetched succesfully");
			return new ResponseEntity<List<Friend>>(friendsList,HttpStatus.OK);
		} else {
			log.info("Friend list for "+loginName+" was not fetched succesfully");
			return new ResponseEntity<List<Friend>>(friendsList,HttpStatus.NOT_FOUND);
		}
	}
}
