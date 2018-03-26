package com.niit.dao;

import java.util.List;
import com.niit.model.Forum;
import com.niit.model.ForumComment;

public interface ForumDAO {
	public boolean addForum(Forum forum);
	public boolean deleteForum(Forum forum);
	public boolean updateForum(Forum forum);
	public List<Forum> forumListUser(String loginName);
	public boolean approveForum(Forum forum);
	public boolean rejectForum(Forum forum);
	public Forum getForum(int forumId);
	public List<Forum> forumList();
	public boolean incrementLikes(Forum forum);
	public List<Forum> getUnapprovedForums();
	public List<Forum> getApprovedForums();
	public List<Forum> getToBeApprovedForums();
	
	public boolean addForumComment(ForumComment forumComment);
	public boolean deleteForumComment(ForumComment forumComment);
	public boolean editForumComment(ForumComment forumComment);
	public ForumComment getComment(int commentId);
	public List<ForumComment> getForumComments(int forumId);
}
