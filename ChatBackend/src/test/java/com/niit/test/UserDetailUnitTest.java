package com.niit.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.niit.dao.UserDetailDAO;
import com.niit.model.UserDetail;

public class UserDetailUnitTest {
	static UserDetailDAO userDetailDAO;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void executeFirst() {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext();
		context.scan("com.niit");
		context.refresh();
		userDetailDAO=(UserDetailDAO)context.getBean("userDetailDAO");
	}
	
	@Ignore
	@Test
	public void addUserDetailTest() {
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Rohit");
		userDetail.setUsername("Rohit Kumar");
		userDetail.setPassword("test");
		userDetail.setMobileNo("9784561586");
		userDetail.setEmailId("rohit@some.com");
		userDetail.setAddress("No.5, Kennedy Street, West Tambaram, Chennai - 600005");
		assertTrue("Problem in User Addition",userDetailDAO.addUserDetail(userDetail));
	}
	
	@Ignore
	@Test
	public void deleteUserDetailTest() {
		UserDetail userDetail = userDetailDAO.viewUserDetailByloginName("");
		assertTrue("Problem in Product Insertion",userDetailDAO.deleteUserDetail(userDetail));
	}
	
	@Ignore
	@Test
	public void viewUserDetailByUsernameTest() {
		UserDetail user = userDetailDAO.viewUserDetailByloginName("Anu");
		assertNotNull("Problem in get Product",user);
		System.out.println("Login name is "+user.getLoginName());
		System.out.println("User Name is "+user.getUsername());
		System.out.println("Password is "+user.getPassword());
		System.out.println("Mobile number is "+user.getMobileNo());
		System.out.println("Email ID is "+user.getEmailId());
		System.out.println("Address is "+user.getAddress());
	}	
	
	@Ignore
	@Test
	public void updateUserDetailTest() {
		UserDetail user = userDetailDAO.viewUserDetailByloginName("Anu");
		user.setEmailId("anuk@friend.com");
		assertTrue("Problem in Product Insertion",userDetailDAO.updateUserDetail(user));
	}
	@Ignore	
	@Test
	public void listUserDetailsTest(){
		List<UserDetail> listUserDetails = userDetailDAO.listUserDetails();
		assertNotNull("Issue in listing user details..",listUserDetails);
		
		System.out.println("Login Name \t User Name\t Mobile Number\t\t Email ID\t\t\t Address");
		for(UserDetail user:listUserDetails) {
			System.out.print(user.getLoginName()+"\t\t");
			System.out.print(user.getUsername()+"\t\t");
			System.out.print(user.getMobileNo()+"\t\t");
			System.out.print(user.getEmailId()+"\t\t");
			System.out.println(user.getAddress());
		}
	}
	
	@Test
	public void authenticateUserTest() {
		UserDetail userDetail = new UserDetail();
		userDetail.setLoginName("Anu");
		userDetail.setPassword("test");
		assertTrue("Login successful",userDetailDAO.authenticateUser(userDetail));
	}
}