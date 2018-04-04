package com.niit.restcontroller;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.JobDAO;
import com.niit.model.Job;

@RestController
public class JobController {
	private static final Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	JobDAO jobDAO;
	
	@PostMapping("/addJob")
	public ResponseEntity<String> addJob(@RequestBody Job job) {
		job.setJobPostedOn(new Date());
		job.setStatus("Active");
		
		log.debug("Job Desig: "+job.getJobDesignation());
		log.debug("Job Desc: "+job.getJobDesc());
		log.debug("Company: "+job.getCompany());
		log.debug("Skillset: "+job.getSkillSet());
		log.debug("Location: "+job.getLocation());
		log.debug("Salary: "+job.getSalary());
		
		if(jobDAO.addJob(job)>0) {
			log.info("Adding new job is successful");
			return new ResponseEntity<String>("Job posted",HttpStatus.OK);
		} else {
			log.info("Adding new job was not successful");
			return new ResponseEntity<String>("Error in posting the job",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@DeleteMapping("/deleteJob/{jobId}")
	public ResponseEntity<String> deleteJob(@PathVariable("jobId")int jobId) {
		Job job = jobDAO.viewJob(jobId);
		
		if(jobDAO.deleteJob(job)) {
			log.info("Deleting job is successful");
			return new ResponseEntity<String>("Job deleted",HttpStatus.OK);
		} else {
			log.info("Deleting job was not successful");
			return new ResponseEntity<String>("Error in deleting the job",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/updateJob/{jobId}")
	public ResponseEntity<String> updateJob(@PathVariable("jobId")int jobId, @RequestBody Job job) {
		
		if(jobDAO.updateJob(job)) {
			log.info("Updating job is successful");
			return new ResponseEntity<String>("Job posted",HttpStatus.OK);
		} else {
			log.info("Updating job was not successful");
			return new ResponseEntity<String>("Error in posting the job",HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/getJobDetails/{jobId}")
	public ResponseEntity<Job> viewJob(@PathVariable("jobId")int jobId){
		Job job = jobDAO.viewJob(jobId);
		if(job!=null) {
			log.info("Retrieving job is successful");
			return new ResponseEntity<Job>(job,HttpStatus.OK);
		} else {
			log.info("Retrieving job was not successful");
			return new ResponseEntity<Job>(job,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/viewAllJobs")
	public ResponseEntity<List<Job>> viewAllJobs(){
		List<Job> listOfJobs = jobDAO.jobList();
		if(listOfJobs!=null) {
			log.info("Retrieving job is successful");
			return new ResponseEntity<List<Job>>(listOfJobs,HttpStatus.OK);
		} else {
			log.info("Retrieving job was not successful");
			return new ResponseEntity<List<Job>>(listOfJobs,HttpStatus.NOT_FOUND);
		}
	}
}