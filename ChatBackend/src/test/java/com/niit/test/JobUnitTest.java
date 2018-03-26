package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.JobDAO;
import com.niit.model.Job;
import com.niit.model.UserDetail;
import com.niit.model.ApplyJob;

public class JobUnitTest {
	static JobDAO jobDAO;
	
	@BeforeClass
	public static void executeFirst() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();		
		jobDAO = (JobDAO)context.getBean("jobDAO");
	}
	
	@Ignore
	@Test
	public void addJobTest(){
		Job job = new Job();
		job.setJobDesignation("Web Developer");
		job.setJobDesc("Developer with a minimum of 1 year of experience. Any link to already developed website is appreciated");
		job.setSkillSet("PHP, HTML, CSS, Bootstrap");
		job.setLocation("Bangalore");
		job.setCompany("ABC Limited");
		job.setJobPostedOn(new Date());
		job.setSalary("2.5 LPA");
		job.setStatus("Active");
		int jobId = jobDAO.addJob(job);
		System.out.println("Job ID is: "+jobId);
		assertTrue("Issue in adding job to the database",jobId!=0);
	}	
	
	@Ignore
	@Test
	public void deleteJobTest(){
		Job job = jobDAO.viewJob(50400);
		assertTrue("Problem in deleting the job",jobDAO.deleteJob(job));
	}
	
	@Ignore
	@Test
	public void updateJobTest(){
		Job job = jobDAO.viewJob(50400);
		job.setStatus("Active");
		assertTrue("Problem in deleting the job",jobDAO.updateJob(job));
	}
	
	@Ignore
	@Test
	public void viewJobTest() {
		Job job = jobDAO.viewJob(50400);
		assertNotNull("Problem in retrieving job usig jobId",job);
		System.out.println("Job ID: "+job.getJobId());
		System.out.println("Job Designation: "+job.getJobDesignation());
		System.out.println("Status: "+job.getStatus());
	}
	
	@Ignore
	@Test
	public void jobListTest(){
		List<Job> jobList = jobDAO.jobList();
		assertNotNull("Problem in retrieving job list",jobList);
		System.out.println("Job ID \t\t Designation \t\t Status \t\t");
		for(Job job:jobList) {
			System.out.print(job.getJobId()+"\t\t");
			System.out.print(job.getJobDesignation()+"\t");
			System.out.println(job.getStatus()+"\t");
		}		
	}	
	
	@Ignore
	@Test
	public void applyJobTest(){
		Job job = new Job();
		job.setJobId(50400);
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Subha");
		
		ApplyJob appln = new ApplyJob();
		appln.setJob(job);
		appln.setAppliedDate(new Date());
		appln.setUserDetail(userDetail);
		
		assertTrue("Problem in applying for job!",(jobDAO.applyJob(appln)>0));
	}
	
	@Test
	public void viewAppliedJobsTest(){
		List<ApplyJob> applnList = jobDAO.viewAppliedJobs("Subha");
		assertNotNull("Problem in retrieving the list",applnList);
		System.out.println("Application ID \t\t Application Status \t Job Designation \t\tApplied By");
		for(ApplyJob appln: applnList) {
			System.out.print(appln.getJobApplicationId()+"\t\t\t");
			System.out.print(appln.getStatus()+"\t\t\t");
			System.out.print(appln.getJob().getJobDesignation()+"\t\t\t");
			System.out.println(appln.getUserDetail().getLoginName()+"\t\t");
		}		
	}
	

	@Test
	public void viewAllJobApplicationsTest(){
		List<ApplyJob> applnList = jobDAO.viewAllJobApplications();
		assertNotNull("Problem in retrieving the list",applnList);
		System.out.println("Application ID \t\t Application Status \t Job Designation \t\tApplied By");
		for(ApplyJob appln: applnList) {
			System.out.print(appln.getJobApplicationId()+"\t\t\t");
			System.out.print(appln.getStatus()+"\t\t\t");
			System.out.print(appln.getJob().getJobDesignation()+"\t\t\t");
			System.out.println(appln.getUserDetail().getLoginName()+"\t\t");
		}
	}
	
	@Ignore
	@Test
	public void withdrawApplicationTest(){
		ApplyJob appln = new ApplyJob();
		appln = jobDAO.viewJobAppln(50050);
		assertTrue("Problem in withdrawing the application",jobDAO.withdrawApplication(appln));
	}
	
	@Ignore
	@Test
	public void viewJobApplnTest() {
		ApplyJob appln = new ApplyJob();
		appln = jobDAO.viewJobAppln(50050);
		assertNotNull("Problem in withdrawing the application",appln);
	}
}
