package com.niit.dao;

import java.util.List;

import com.niit.model.UserDetail;

public interface UserDetailDAO {
	public boolean addUserDetail(UserDetail userDetail);
	public boolean deleteUserDetail(UserDetail userDetail);
	public boolean updateUserDetail(UserDetail userDetail);
	public UserDetail viewUserDetailByloginName(String loginName);
	public List<UserDetail> listUserDetails();	
	public boolean authenticateUser(UserDetail userDetail);
}
