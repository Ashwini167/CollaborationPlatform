var myApp = angular.module("myApp",['ngRoute']);

myApp.config(function($routeProvider,$locationProvider) {
	//alert('Inside routeProvider');
	$locationProvider.hashPrefix('');
	$routeProvider.when('/',{templateUrl:'/index.html'})
					.when("/home",{templateUrl:"template/home.html"})
					.when("/login",{templateUrl:"template/login.html"})
					.when("/register",{templateUrl:"template/register.html"})
					.when("/contactUs",{templateUrl:"template/contactUs.html"})	
});