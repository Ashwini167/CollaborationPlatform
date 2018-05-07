package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
		requestor.setLoginName("Subha");
		friend.setRequestor(requestor);
		UserDetail toFriend = new UserDetail();
		toFriend.setLoginName("Guru");
		friend.setFriend(toFriend);
		
		friend.setIsOnline("Y");
		friend.setRequestedOrAcceptedOn(new Date());
		friend.setStatus("Requested");
		
		assertTrue("Problem in addingFriend.",friendDAO.addFriendReq(friend));
	}
	
	@Ignore
	@Test 
	public void getFriendTest() {
		Friend frnd = friendDAO.getFriendRequest(2300);
		assertNotNull("Problem in retrieving friend request details",frnd);
		System.out.println("Friend ID:::: "+frnd.getFriendId()+">>>Requestor:::: "+frnd.getRequestor().getLoginName()+">>>ToFriend:::: "+frnd.getFriend().getLoginName());
	}
	
	@Ignore
	@Test
	public void viewFriendRequestsTest() {
		Map<Integer, UserDetail> friendRequests = friendDAO.viewFriendRequests("Sathish");
		System.out.println("Requested friends are ");
		for(Map.Entry<Integer,UserDetail> entry : friendRequests.entrySet()) {
			System.out.println(entry.getKey()+"::::"+entry.getValue().getLoginName());
		}
		assertNotNull("Problem in retrieving friend requests",friendRequests);
	}
	
	@Ignore
	@Test
	public void acceptFriendTest() {
		Friend frnd = friendDAO.getFriendRequest(2450);
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
		List<Friend> friendsList = friendDAO.viewFriendsList("Subha");
		System.out.println("FriendID\tFrom\t\tTo");
		for(Friend friend:friendsList) {
			System.out.println(friend.getFriendId()+"\t"+friend.getRequestor().getLoginName()+"\t"+friend.getFriend().getLoginName());
		}
		assertNotNull("Problem in retrieving friends list",friendsList);
	}
	
	
	@Test
	public void viewSuggestedFriendsTest() {
		List<UserDetail> suggestedFriends = friendDAO.viewSuggestedFriends("Anu");
		System.out.println("Size of the list: "+suggestedFriends.size());
		System.out.println("Suggested friends: ");
		for(UserDetail user:suggestedFriends) {
			System.out.println(user.getLoginName());
		}
	}	
}