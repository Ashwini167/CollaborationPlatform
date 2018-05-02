package com.niit.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.niit.model.ApplyJob;
import com.niit.model.Job;

@Repository("jobDAO")
public class JobDAOImpl implements JobDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public int addJob(Job job) {
		try {
			System.out.println("Inside add job method");
			return (int)sessionFactory.getCurrentSession().save(job);
		} catch (Exception e){
			System.out.println("There is an exception here! \n"+e);
			return 0;
		}		
	}
	
	@Transactional
	@Override
	public boolean deleteJob(Job job) {
		try {
			job.setStatus("D");
			sessionFactory.getCurrentSession().update(job);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}

	@Transactional
	@Override
	public boolean updateJob(Job job) {
		try {
			sessionFactory.getCurrentSession().update(job);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
	
	@Transactional
	@Override
	public Job viewJob(int jobId) {
		try {
			return (Job)sessionFactory.getCurrentSession().get(Job.class, jobId);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Job> jobList() {
		try {
			return (List<Job>)sessionFactory.getCurrentSession().createQuery("from Job where status='A'").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public int applyJob(ApplyJob appln) {
		try {
			appln.setStatus("A");
			return (int)sessionFactory.getCurrentSession().save(appln);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ApplyJob> viewAppliedJobs(String loginName) {
		try {
			return (List<ApplyJob>)sessionFactory.getCurrentSession().createQuery("from ApplyJob where loginName='"+loginName+"'").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}
	
	@Transactional
	@Override
	public ApplyJob viewJobAppln(int jobId,String loginName) {
		try {
			return (ApplyJob)sessionFactory.getCurrentSession().createQuery("from ApplyJob where jobId="+jobId+"and loginName='"+loginName+"'").list().get(0);
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<ApplyJob> viewAllJobApplications() {
		try {
			return (List<ApplyJob>)sessionFactory.getCurrentSession().createQuery("from ApplyJob").list();
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return null;
		}
	}

	@Transactional
	@Override
	public boolean withdrawApplication(ApplyJob appln) {
		try {
			appln.setStatus("W");
			sessionFactory.getCurrentSession().update(appln);
			return true;
		}catch(Exception e) {
			System.out.println("There is an exception here! \n"+e);
			return false;
		}
	}
}