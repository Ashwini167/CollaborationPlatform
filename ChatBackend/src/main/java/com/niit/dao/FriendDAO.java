package com.niit.dao;

import java.util.List;
import java.util.Map;
import com.niit.model.Friend;
import com.niit.model.UserDetail;

public interface FriendDAO {
	public boolean addFriendReq(Friend friend);
	public boolean acceptFriend(Friend friend);
	public boolean rejectFriend(Friend friend);
	public Map<Integer,UserDetail> viewFriendRequests(String loginName);
	public List<Friend> viewFriendsList(String loginName);
	public List<UserDetail> viewSuggestedFriends(String loginName);
	public Friend getFriendRequest(int friendId);
}