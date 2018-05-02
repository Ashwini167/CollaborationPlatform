package com.niit.dao;

import java.util.List;
import com.niit.model.ApplyJob;
import com.niit.model.Job;

public interface JobDAO {
	public int addJob(Job job);
	public boolean deleteJob(Job job);
	public boolean updateJob(Job job);
	public Job viewJob(int jobId);
	public List<Job> jobList();	
	
	public int applyJob(ApplyJob appln);
	public List<ApplyJob> viewAppliedJobs(String loginName);
	public List<ApplyJob> viewAllJobApplications();
	public boolean withdrawApplication(ApplyJob appln);
	public ApplyJob viewJobAppln(int jobId, String loginName);
}