package com.niit.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.niit.dao.BlogDAO;
import com.niit.model.Blog;

@RestController
public class BlogController {	
	@Autowired
	BlogDAO blogDAO;
	
	@GetMapping("/showAllBlogs")
	public ResponseEntity<List<Blog>> showAllBlogsOfUser() {
		List<Blog> listUsersBlog = blogDAO.blogListUser("Anu");
		if(listUsersBlog!=null) {
			return new ResponseEntity<List<Blog>>(listUsersBlog,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<List<Blog>>(listUsersBlog,HttpStatus.INTERNAL_SERVER_ERROR);
		}		
	}
}