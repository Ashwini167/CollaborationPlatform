package com.niit.test;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.ForumDAO;
import com.niit.model.Forum;
import com.niit.model.ForumComment;
import com.niit.model.UserDetail;

public class ForumUnitTest {
static ForumDAO forumDAO;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		forumDAO = (ForumDAO) context.getBean("forumDAO");
		//context.close();
	}	
	
	@Ignore
	@Test
	public void addForumTest() {		
		Forum forum = new Forum();
		forum.setForumName("Time Management");
		forum.setForumContent("Managing time is something that has been spoken about very frequently. Why is it important? How does it help one professionally and personally?");
		forum.setCreatedDate(new Date());
		forum.setLikes(0);
		forum.setStatus("create");		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Loga");
		forum.setUserDetail(userDetail);
		
		assertTrue("Problem in adding the forum",forumDAO.addForum(forum));
	}
	
	@Ignore
	@Test
	public void deleteForumUnitTest(){
		Forum forum = forumDAO.getForum(50150);
		assertTrue("Problem in deleting forum",forumDAO.deleteForum(forum));
		
		System.out.println("Forum ID: "+forum.getForumId());
		System.out.println("Forum Name: "+forum.getForumName());
		System.out.println("Likes: "+forum.getLikes());
		System.out.println("Status: "+forum.getStatus());
	}
	
	@Ignore
	@Test
	public void updateForumUnitTest(){ 
		Forum forum = forumDAO.getForum(50100);
		forum.setForumContent("Did you know that you can automate every single repetitive task using MS excel? Stay tuned to this forum for more information on MS Excel.");
		assertTrue("Problem in updating the forum",forumDAO.updateForum(forum));
		System.out.println("Forum ID: "+forum.getForumId());
		System.out.println("Forum Name: "+forum.getForumName());
		System.out.println("Likes: "+forum.getLikes());
		System.out.println("Status: "+forum.getStatus());
	}
	
	@Ignore
	@Test
	public void forumListUserUnitTest(){ 
		List<Forum> forumListForUser =  forumDAO.forumListUser("Loga");
		assertNotNull("Problem in retrieving the forum list using login name", forumListForUser);
		System.out.println("Forum ID \t Status \t Likes \tCreated Date \t\t Forum Name");
		for(Forum forum: forumListForUser) {
			System.out.print(forum.getForumId()+"\t\t");
			System.out.print(forum.getStatus()+"\t\t");
			System.out.print(forum.getLikes()+"\t");
			System.out.print(forum.getCreatedDate()+"\t\t");
			System.out.println(forum.getForumName());
		}		
	}
	
	@Ignore
	@Test
	public void approveForumUnitTest(){ 
		Forum forum = forumDAO.getForum(50100);
		assertTrue("Problem in updating the forum",forumDAO.approveForum(forum));
		System.out.println("Forum ID: "+forum.getForumId());
		System.out.println("Forum Name: "+forum.getForumName());
		System.out.println("Likes: "+forum.getLikes());
		System.out.println("Status: "+forum.getStatus());
	}
	
	@Ignore
	@Test
	public void rejectForumUnitTest(){ 
		Forum forum = forumDAO.getForum(50050);
		assertTrue("Problem in updating the forum",forumDAO.rejectForum(forum));
		System.out.println("Forum ID: "+forum.getForumId());
		System.out.println("Forum Name: "+forum.getForumName());
		System.out.println("Likes: "+forum.getLikes());
		System.out.println("Status: "+forum.getStatus());
	}
	
	@Ignore
	@Test
	public void getForumUnitTest(){ 
		Forum forum = forumDAO.getForum(50050);
		assertNotNull("Problem in retrieving the complete forum list",forum);
		System.out.println("Forum ID: "+forum.getForumId());
		System.out.println("Forum Name: "+forum.getForumName());
		System.out.println("Likes: "+forum.getLikes());
		System.out.println("Status: "+forum.getStatus());
	}
	
	@Ignore
	@Test
	public void incrementLikesUnitTest(){ 
		Forum forum = forumDAO.getForum(50100);
		assertTrue("Problem while incrementing likes",forumDAO.incrementLikes(forum));
	}
	
	@Ignore
	@Test
	public void getUnapprovedForumsUnitTest(){ 
		List<Forum> unapprovedForums =  forumDAO.getUnapprovedForums();
		assertNotNull("Problem in retrieving the forum list using login name", unapprovedForums);
		System.out.println("========================================================================================================");
		System.out.println("Unapproved forums are as follows: ");
		System.out.println("Forum ID \t Status \t Likes \tCreated Date \t\t Forum Name");
		for(Forum forum: unapprovedForums) {
			System.out.print(forum.getForumId()+"\t\t");
			System.out.print(forum.getStatus()+"\t\t");
			System.out.print(forum.getLikes()+"\t");
			System.out.print(forum.getCreatedDate()+"\t\t");
			System.out.println(forum.getForumName());
		}
		System.out.println("========================================================================================================");		
	}
	
	@Ignore
	@Test
	public void getApprovedForumsUnitTest(){ 
		List<Forum> approvedForums =  forumDAO.getApprovedForums();
		assertNotNull("Problem in retrieving the forum list using login name", approvedForums);
		System.out.println("========================================================================================================");
		System.out.println("Approved forums are as follows: ");
		System.out.println("Forum ID \t Status \t Likes \tCreated Date \t\t Forum Name");
		for(Forum forum: approvedForums) {
			System.out.print(forum.getForumId()+"\t\t");
			System.out.print(forum.getStatus()+"\t\t");
			System.out.print(forum.getLikes()+"\t");
			System.out.print(forum.getCreatedDate()+"\t\t");
			System.out.println(forum.getForumName());
		}
		System.out.println("========================================================================================================");
	}
	
	@Ignore
	@Test
	public void getToBeApprovedForumsUnitTest(){ 
		List<Forum> toBeApprovedForums =  forumDAO.getToBeApprovedForums();
		assertNotNull("Problem in retrieving the forum list using login name", toBeApprovedForums);
		System.out.println("========================================================================================================");
		System.out.println("To be approved forums are as follows: ");
		System.out.println("Forum ID \t Status \t Likes \tCreated Date \t\t Forum Name");
		for(Forum forum: toBeApprovedForums) {
			System.out.print(forum.getForumId()+"\t\t");
			System.out.print(forum.getStatus()+"\t\t");
			System.out.print(forum.getLikes()+"\t");
			System.out.print(forum.getCreatedDate()+"\t\t");
			System.out.println(forum.getForumName());
		}
		System.out.println("========================================================================================================");
	}
	
	@Ignore
	@Test
	public void forumListUnitTest(){ 
		List<Forum> forumList =  forumDAO.forumList();
		assertNotNull("Problem in retrieving the forum list using login name", forumList);
		System.out.println("Login name \t Forum ID \t Likes \t Created Date \t Status \t\t Forum Name");
		for(Forum forum: forumList) {
			System.out.print(forum.getUserDetail().getLoginName()+"\t");
			System.out.print(forum.getForumId()+"\t\t");
			System.out.print(forum.getLikes()+"\t");
			System.out.print(forum.getCreatedDate()+"\t\t");
			System.out.print(forum.getStatus()+"\t\t");
			System.out.println(forum.getForumName());
		}	
	}
	
	@Ignore
	@Test
	public void addForumCommentUnitTest() {
		Forum forum = new Forum();
		forum.setForumId(50100);
		
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Loga");
		
		ForumComment comment = new ForumComment();
		comment.setForumCommentText("The top most section of an MS Excel file is called Ribbon. It provides options to perform various activities like Formatting, Formulae, Filtering, etc.");
		comment.setForumCommentDate(new Date());
		comment.setForum(forum);
		comment.setUserDetail(userDetail);
		
		assertTrue("Problem while adding comments to the forum",forumDAO.addForumComment(comment));
	}
	
	@Ignore
	@Test
	public void deleteForumCommentUnitTest() {
		ForumComment comment = forumDAO.getComment(50100);
		assertTrue("Problem in deleting the comment",forumDAO.deleteForumComment(comment));
	}
	
	@Ignore
	@Test
	public void editForumCommentUnitTest() {
		ForumComment comment = forumDAO.getComment(50100);
		comment.setStatus("posted");
		assertTrue("Problem in deleting the comment",forumDAO.editForumComment(comment));
	}
	
	@Ignore
	@Test
	public void getCommentUnitTest() {
		ForumComment comment = forumDAO.getComment(50100);
		assertNotNull("Problem in retrieving comment using commentId",comment);
		System.out.println("Comment ID: "+comment.getForumCommentId());
		System.out.println("Status: "+comment.getStatus());
	}
	
	@Ignore
	@Test
	public void getForumCommentsUnitTest() {
		List<ForumComment> commentsList = forumDAO.getForumComments(50100);
		assertNotNull("Problem in retrieving comments for this forum",commentsList);
		System.out.println("Forum ID \t Comment ID \t Comment Text \t \n=========================================================\n");
		for(ForumComment comment:commentsList) {
			System.out.print(comment.getForum().getForumId()+"\t");
			System.out.print(comment.getForumCommentId()+"\t");
			System.out.println(comment.getForumCommentText());
		}
	}
}