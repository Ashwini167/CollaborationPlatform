package com.niit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@SequenceGenerator(name="friendIdSeq",sequenceName="FRIEND_ID_SEQ")
public class Friend {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="friendIdSeq")
	private int friendId;
	private String status;
	private String isOnline;
	@OneToOne
	@JoinTable(name="requestor")
	@JoinColumn(name="loginName")
	private UserDetail requestor;
	@OneToOne
	@JoinTable(name="Tofriend")
	@JoinColumn(name="loginName")
	private UserDetail friend;
	private Date requestedOrAcceptedOn;	
	
	public int getFriendId() {
		return friendId;
	}
	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public UserDetail getRequestor() {
		return requestor;
	}
	public void setRequestor(UserDetail requestor) {
		this.requestor = requestor;
	}
	public UserDetail getFriend() {
		return friend;
	}
	public void setFriend(UserDetail friend) {
		this.friend = friend;
	}
	public Date getRequestedOrAcceptedOn() {
		return requestedOrAcceptedOn;
	}
	public void setRequestedOrAcceptedOn(Date requestedOrAcceptedOn) {
		this.requestedOrAcceptedOn = requestedOrAcceptedOn;
	}
}