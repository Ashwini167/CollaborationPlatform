package com.niit.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table
@SequenceGenerator(name="forumCommIdSeq", sequenceName="FORUM_COMM_ID_SEQ")
public class ForumComment {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="forumCommIdSeq")
	private int forumCommentId;	
	private String forumCommentText;	
	@OneToOne
	@JoinColumn(name="loginName")
	private UserDetail userDetail;
	@OneToOne
	@JoinColumn(name="forumId")
	private Forum forum;	
	private Date forumCommentDate;
	private String status;
	
	public int getForumCommentId() {
		return forumCommentId;
	}
	public void setForumCommentId(int forumCommentId) {
		this.forumCommentId = forumCommentId;
	}
	public String getForumCommentText() {
		return forumCommentText;
	}
	public void setForumCommentText(String forumCommentText) {
		this.forumCommentText = forumCommentText;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public Forum getForum() {
		return forum;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
	}
	public Date getForumCommentDate() {
		return forumCommentDate;
	}
	public void setForumCommentDate(Date forumCommentDate) {
		this.forumCommentDate = forumCommentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}