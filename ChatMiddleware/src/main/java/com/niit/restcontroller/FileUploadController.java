package com.niit.restcontroller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import com.google.gson.Gson;
import com.niit.dao.ProfilePictureDAO;
import com.niit.model.ProfilePicture;
import com.niit.model.UserDetail;

@RestController
public class FileUploadController {
	private static final Gson gson = new Gson();
	private static final Logger log = LoggerFactory.getLogger(BlogController.class);
	
	@Autowired 
	ProfilePictureDAO profilePictureDAO;
	
	@RequestMapping(value="/doUpload",method=RequestMethod.POST)
	public ResponseEntity<String> uploadPicture(@RequestParam(value="file") CommonsMultipartFile profilePic, HttpSession session){
		UserDetail userDetail = ((UserDetail)session.getAttribute("loggedInUser"));
		if(userDetail==null) {
			log.info("User detail null in session");
			return new ResponseEntity<String>(gson.toJson("Unauthorized user"),HttpStatus.NOT_FOUND);
		}
		else {
			log.info("Adding profile pic");
			ProfilePicture profilePicture = new ProfilePicture();
			profilePicture.setUserDetail(userDetail);
			profilePicture.setImage(profilePic.getBytes());
			profilePictureDAO.savePicture(profilePicture);
			return new ResponseEntity<String>(gson.toJson("Profile pic upload success"),HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/getImage/{loginName}",method=RequestMethod.GET) 
	public @ResponseBody byte[] getProfilePicture(@PathVariable("loginName")String loginName,HttpSession session){
		UserDetail userDetail = ((UserDetail)session.getAttribute("loggedInUser"));
		if(userDetail==null)
			return null;
		else {
			ProfilePicture profilePicture = profilePictureDAO.viewProfilePicture(userDetail.getLoginName());
			if(profilePicture==null)
				return null;
			else
				return profilePicture.getImage();
		}			
	}
}