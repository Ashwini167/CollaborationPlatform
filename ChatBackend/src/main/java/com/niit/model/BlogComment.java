package com.niit.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="blogCommIdSeq",sequenceName="BLOG_COMM_ID_SEQ")
public class BlogComment {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="blogCommIdSeq")
	private int commentId;	
	private String commentText;	
	@OneToOne
	@JoinColumn(name="loginName")
	private UserDetail userDetail;
	@OneToOne
	@JoinColumn(name="blogId")
	private Blog blog;	
	private Date commentDate;
	private String status;
	
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	public Date getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}