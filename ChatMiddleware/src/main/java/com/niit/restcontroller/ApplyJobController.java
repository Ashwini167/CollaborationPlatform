package com.niit.restcontroller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.niit.dao.JobDAO;
import com.niit.model.ApplyJob;
import com.niit.model.Job;
import com.niit.model.UserDetail;

@RestController
public class ApplyJobController {
	private static final Logger log = LoggerFactory.getLogger(JobController.class);
	private static final Gson gson = new Gson();
	
	@Autowired
	JobDAO jobDAO;
	
	@GetMapping("/applyJob/{jobId}")
	public ResponseEntity<String> applyJob(@PathVariable("jobId")int jobId, HttpSession session) {
		UserDetail userDetail = (UserDetail)session.getAttribute("loggedInUser");
		ApplyJob applyJob = new ApplyJob();
		applyJob.setAppliedDate(new Date());
		applyJob.setStatus("Active");
		Job job = jobDAO.viewJob(jobId);
		applyJob.setJob(job);
		applyJob.setUserDetail(userDetail);
		int applicationId = jobDAO.applyJob(applyJob);
		if(applicationId>0) {
			log.info("Job applicaiton sent successfully");
			return new ResponseEntity<String>(gson.toJson(applicationId),HttpStatus.OK);
		} else {
			log.info("Error while sending job application");
			return new ResponseEntity<String>(gson.toJson(applicationId),HttpStatus.INTERNAL_SERVER_ERROR);
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
	
	@GetMapping("/viewJobApplications")
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
	
	@GetMapping("/viewJobApplication/{jobId}")
	public ResponseEntity<ApplyJob> viewJobApplication(@PathVariable("jobId")int jobId, HttpSession session){
		UserDetail userDetail = (UserDetail)session.getAttribute("loggedInUser");
		ApplyJob jobAppln = jobDAO.viewJobAppln(jobId,userDetail.getLoginName());
		if(jobAppln!=null) {
			log.info("Job applicaiton withdrawn");
			return new ResponseEntity<ApplyJob>(jobAppln,HttpStatus.OK);
		} else {
			log.info("Unable to withdrawthe job application");
			return new ResponseEntity<ApplyJob>(jobAppln,HttpStatus.OK);
		}
	}
	
	@GetMapping("/withdrawApplication/{jobId}")
	public ResponseEntity<String> withdrawJobApplication(@PathVariable("jobId")int jobId, HttpSession session){
		UserDetail userDetail = (UserDetail)session.getAttribute("loggedInUser");
		ApplyJob jobAppln = jobDAO.viewJobAppln(jobId,userDetail.getLoginName());
		if(jobDAO.withdrawApplication(jobAppln)) {
			log.info("Job applicaiton withdrawn");
			return new ResponseEntity<String>(gson.toJson("Application withdrawn"),HttpStatus.OK);
		} else {
			log.info("Unable to withdrawthe job application");
			return new ResponseEntity<String>(gson.toJson("Application withdrawal failed"),HttpStatus.OK);
		}
	}
	
	@PutMapping("/applicationStatus")
	public ResponseEntity<Map<Integer,Boolean>> applicationStatus(@RequestBody List<Job> jobList,HttpSession session){
		log.info("Inside applicationStatus method");
		Map<Integer,Boolean> appliedStatus = new HashMap<Integer,Boolean>();
		UserDetail userDetail = (UserDetail)session.getAttribute("loggedInUser");
		if(userDetail!=null) {
			List<ApplyJob> jobsByUser = jobDAO.viewAppliedJobs(userDetail.getLoginName());
			for(Job job:jobList) {
				log.info("Iterating over jobList");
				for(ApplyJob appln:jobsByUser) {
					log.info("Iterating over job applications");
					if(job.getJobId()==appln.getJob().getJobId() && appln.getStatus().equals("A"))
						appliedStatus.put(job.getJobId(), true);
				}			
			}
			for(Map.Entry<Integer,Boolean> entry : appliedStatus.entrySet()) {
			    log.debug(entry.getKey()+"::::"+entry.getValue());
			}
		}
		if(appliedStatus.size()>0) {
			log.info("Map populated with atleast one value");
			return new ResponseEntity<Map<Integer,Boolean>>(appliedStatus,HttpStatus.OK);
		} else {
			log.info("No applied jobs");
			return new ResponseEntity<Map<Integer,Boolean>>(appliedStatus,HttpStatus.NOT_FOUND);
		}			
	}	
}