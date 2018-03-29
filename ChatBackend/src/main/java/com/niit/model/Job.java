package com.niit.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table
@SequenceGenerator(name="jobIdSeq", sequenceName="JOB_ID_SEQ")
public class Job {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="jobIdSeq")
	@Column(name="jobId")
	private int jobId;
	
	private String jobDesignation;
	private String company;
	private String salary;
	private String location;
	@Column(length=250)
	private String jobDesc;
	private String skillSet;
	private String status;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MMM-yyyy")
	private Date jobPostedOn;
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobDesignation() {
		return jobDesignation;
	}
	public void setJobDesignation(String jobDesignation) {
		this.jobDesignation = jobDesignation;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getSkillSet() {
		return skillSet;
	}
	public void setSkillSet(String skillSet) {
		this.skillSet = skillSet;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getJobPostedOn() {
		return jobPostedOn;
	}
	public void setJobPostedOn(Date jobPostedOn) {
		this.jobPostedOn = jobPostedOn;
	}
}