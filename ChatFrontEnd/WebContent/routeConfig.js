var myApp = angular.module("myApp",['ngRoute']);

myApp.config(function($routeProvider,$locationProvider) {
	//alert('Inside routeProvider');
	$locationProvider.hashPrefix('');
	$routeProvider.when('/#/',{templateUrl:'/index.html'})
					.when("/home",{templateUrl:"Home/home.html"})
					.when("/login",{templateUrl:"User/login.html"})
					.when("/register",{templateUrl:"User/register.html"})
					.when("/contactUs",{templateUrl:"Home/contactUs.html"})
					.when("/myBlog",{templateUrl:"Blog/myBlog.html"})
});