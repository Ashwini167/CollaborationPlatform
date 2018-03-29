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

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
@SequenceGenerator(name="applIdSeq",sequenceName="APPLN_ID_SEQ")
public class ApplyJob {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="applIdSeq")
	private int jobApplicationId;
	@OneToOne
	@JoinColumn(name="jobId")
	private Job job;
	@OneToOne
	@JoinColumn(name="loginName")
	private UserDetail userDetail;
	private String status;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MMM-yyyy")
	private Date appliedDate;
	
	public int getJobApplicationId() {
		return jobApplicationId;
	}
	public void setJobApplicationId(int jobApplicationId) {
		this.jobApplicationId = jobApplicationId;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(Date appliedDate) {
		this.appliedDate = appliedDate;
	}
}