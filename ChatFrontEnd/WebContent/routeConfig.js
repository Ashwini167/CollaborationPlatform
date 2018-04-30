var myApp = angular.module("myApp",['ngRoute','ngCookies']);

myApp.config(function($routeProvider,$locationProvider) {
	//alert('Inside routeProvider');
	$locationProvider.hashPrefix('');
	$routeProvider.when('/#/',{templateUrl:'/index.html'})
					.when("/home",{templateUrl:"Home/home.html"})
					.when("/login",{templateUrl:"User/login.html"})
					.when("/register",{templateUrl:"User/register.html"})
					.when("/contactUs",{templateUrl:"Home/contactUs.html"})
					.when("/myBlog",{templateUrl:"Blog/myBlog.html"})
					.when("/addBlog",{templateUrl:"Blog/blog.html"})
					.when("/logout",{templateUrl:"Home/logout.html"})
					.when("/viewBlogs",{templateUrl:"Blog/viewApprovedBlogs.html"})
					.when("/adminBlog",{templateUrl:"Blog/adminBlog.html"})
					.when("/readBlog",{templateUrl:"Blog/readBlog.html"})
					.when("/manageBlogs",{templateUrl:"Blog/adminBlog.html"})
					.when("/addJob",{templateUrl:"Job/addJob.html"})
					.when("/viewJobs",{templateUrl:"Job/viewJobs.html"})
					.when("/updateProfile",{templateUrl:"User/ProfilePicture.html"})
					.when("/friendsPage",{templateUrl:"Friend/friends.html"})
					.when("/editBlog",{templateUrl:"Blog/editBlog.html"})
					.when("/chat",{templateUrl:"Chat/Chat.html"})
					.when("/jobApplied",{templateUrl:"Job/jobApplied.html"})
					.when("/editJob",{templateUrl:"Job/editJob.html"})
					.when("/myAppliedJobs",{templateUrl:"Job/myAppliedJobs.html"})
});

myApp.run(function($rootScope,$cookies){
	console.log('Inside Run function of routeConfig.js')
	console.log($rootScope.currentUser);
	if($rootScope.currentUser==undefined) {
		console.log('rootscope is undefined');
		$rootScope.currentUser = $cookies.getObject("userDetails");
	}
});