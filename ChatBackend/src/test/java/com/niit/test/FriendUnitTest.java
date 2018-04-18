package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.FriendDAO;
import com.niit.model.Friend;
import com.niit.model.UserDetail;

public class FriendUnitTest {
	static FriendDAO friendDAO;
	
	@BeforeClass
	public static void executeFirst() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		friendDAO = (FriendDAO) context.getBean("friendDAO");
	}
	
	@Ignore
	@Test
	public void addFriendTest() {
		Friend friend = new Friend();
		UserDetail requestor = new UserDetail();
		requestor.setLoginName("Anu");
		friend.setRequestor(requestor);
		UserDetail toFriend = new UserDetail();
		toFriend.setLoginName("Subha");
		friend.setFriend(toFriend);
		
		friend.setIsOnline("Y");
		friend.setRequestedOrAcceptedOn(new Date());
		friend.setStatus("Requested");
		
		assertTrue("Problem in addingFriend.",friendDAO.addFriendReq(friend));
	}
	
	@Ignore
	@Test 
	public void getFriendRequestTest() {
		Friend frnd = friendDAO.getFriendRequest(100);
		assertNotNull("Problem in retrieving friend request details",frnd);
	}
	
	@Ignore
	@Test
	public void viewFriendRequestsTest() {
		List<Friend> friendRequests = friendDAO.viewFriendRequests("Anu");
		System.out.println("Friend ID \t Requestor Name \t To Friend \t Status");
		for(Friend friend:friendRequests) {
			System.out.print(friend.getFriendId()+"\t\t");
			System.out.print(friend.getRequestor().getUsername()+"\t\t");
			System.out.print(friend.getFriend().getUsername()+"\t");
			System.out.println(friend.getStatus());
		}
		assertNotNull("Problem in retrieving friend requests",friendRequests);
	}
	
	@Ignore
	@Test
	public void acceptFriendTest() {
		Friend frnd = friendDAO.getFriendRequest(50);
		assertTrue("Problem in accepting friend request!",friendDAO.acceptFriend(frnd));
	}
	
	@Ignore
	@Test
	public void rejectFriendTest() {
		Friend frnd = friendDAO.getFriendRequest(100);
		assertTrue("Problem in rejecting friend request",friendDAO.rejectFriend(frnd));
	}
	
	@Ignore
	@Test
	public void viewFriendsListTest() {
		List<Friend> friendRequests = friendDAO.viewFriendsList("Loga");
		System.out.println("Friend ID \t Friends \t Status");
		for(Friend friend:friendRequests) {
			System.out.print(friend.getFriendId()+"\t\t");
			System.out.print(friend.getFriend().getUsername()+"\t\t");
			System.out.println(friend.getStatus());
		}
		assertNotNull("Problem in retrieving friend requests",friendRequests);
	}
	
	@Test
	public void viewSuggestedFriendsTest() {
		List<UserDetail> suggestedFriends = friendDAO.viewSuggestedFriends("Anu");
		System.out.println("Suggested friends: ");
		for(UserDetail user:suggestedFriends) {
			System.out.println(user.getLoginName());
		}
	}
	
}