package com.niit.restcontroller;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.JobDAO;
import com.niit.model.ApplyJob;
import com.niit.model.Job;
import com.niit.model.UserDetail;

@RestController
public class ApplyJobController {
	private static final Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	JobDAO jobDAO;
	
	@PostMapping("/applyJob")
	public ResponseEntity<String> applyJob(@RequestBody ApplyJob applyJob) {
		//String loginName = (String)session.getAttribute("loginName");
		String  loginName = "Loga";
		int jobId = 50105;		
		applyJob.setAppliedDate(new Date());
		applyJob.setStatus("Active");
		Job job = new Job();
		job.setJobId(jobId);
		applyJob.setJob(job);		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName(loginName);
		applyJob.setUserDetail(userDetail);
		
		if(jobDAO.applyJob(applyJob)>0) {
			log.info("Job applicaiton sent successfully");
			return new ResponseEntity<String>("Applied",HttpStatus.OK);
		} else {
			log.info("Error while sending job application");
			return new ResponseEntity<String>("Error in applying for the job",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/viewAppliedJobs/{loginName}")
	public ResponseEntity<List<ApplyJob>> viewAppliedJobs(@PathVariable("loginName")String loginName) {
		List<ApplyJob> jobList = jobDAO.viewAppliedJobs(loginName);
		if(jobList!=null) {
			log.info("List of applied jobs by user "+loginName+" retrieved");
			return new ResponseEntity<List<ApplyJob>>(jobList,HttpStatus.OK);
		} else {
			log.info("No jobs applied by the user "+loginName);
			return new ResponseEntity<List<ApplyJob>>(jobList,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/viewJobApplications/{loginName}")
	public ResponseEntity<List<ApplyJob>> viewJobApplications() {
		List<ApplyJob> jobList = jobDAO.viewAllJobApplications();
		if(jobList!=null) {
			log.info("List of all applied jobs retrieved");
			return new ResponseEntity<List<ApplyJob>>(jobList,HttpStatus.OK);
		} else {
			log.info("No job applied by any user");
			return new ResponseEntity<List<ApplyJob>>(jobList,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/viewJobApplication/{jobApplicationId}")
	public ResponseEntity<ApplyJob> viewJobApplication(@PathVariable("jobApplicationId")int jobApplicationId){
		ApplyJob jobAppln = jobDAO.viewJobAppln(jobApplicationId);
		if(jobAppln!=null) {
			log.info("Job applicaiton withdrawn");
			return new ResponseEntity<ApplyJob>(jobAppln,HttpStatus.OK);
		} else {
			log.info("Unable to withdrawthe job application");
			return new ResponseEntity<ApplyJob>(jobAppln,HttpStatus.OK);
		}
	}
	
	@PutMapping("/withdrawApplication/{jobApplicationId}")
	public ResponseEntity<String> withdrawJobApplication(@PathVariable("jobApplicationId")int jobApplicationId){
		ApplyJob jobAppln = jobDAO.viewJobAppln(jobApplicationId);
		if(jobDAO.withdrawApplication(jobAppln)) {
			log.info("Job applicaiton withdrawn");
			return new ResponseEntity<String>("Application withdrawn",HttpStatus.OK);
		} else {
			log.info("Unable to withdrawthe job application");
			return new ResponseEntity<String>("Application withdrawal failed",HttpStatus.OK);
		}
	}
}