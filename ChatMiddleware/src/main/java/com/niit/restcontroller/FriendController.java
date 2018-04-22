package com.niit.restcontroller;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
