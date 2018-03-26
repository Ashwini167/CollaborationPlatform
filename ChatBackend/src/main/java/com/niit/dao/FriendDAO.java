package com.niit.dao;

import java.util.List;

import com.niit.model.Friend;

public interface FriendDAO {
	public boolean addFriendReq(Friend friend);
	public boolean acceptFriend(Friend friend);
	public boolean rejectFriend(Friend friend);
	public List<Friend> viewFriendRequests(String loginName);
	public List<Friend> viewFriendsList(String loginName);
	public Friend getFriendRequest(int friendId);
}
