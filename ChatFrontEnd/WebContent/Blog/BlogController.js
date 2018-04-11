myApp.controller("BlogController",function($scope, $rootScope, $http, $location){
	console.log('Inside blogController');
	
	$scope.blog = {
			"blogId":0,
			"blogName":'',
			"blogContent":'',
			"createdDate":'',
			"likes":0,
			"status":'',
			"userDetail":{
				"loginName":'',
				"password":'',
				"username":'',
				"emailId":'',
				"mobileNo":'',
				"address":'',
				"role":'',
				"enabled":''				
			},
	};
	
	$scope.listBlog = function($rootScope){
		console.log('Inside retrieve user blog function');
		$http.get('http://localhost:8083/ChatMiddleware/showAllBlogsOfUser')
			.then(function(response) {
				console.log('Inside response');
				console.log(response.data);
				$scope.blogData = response.data;
			});
	}
	
	$scope.addBlog = function(){
		console.log('Inside add blog function');
		console.log('LoginName is: '+$rootScope.currentUser.loginName);
		$http.post('http://localhost:8083/ChatMiddleware/addBlog',$scope.blog)
			.then(function(response) {
				console.log(response.data);
				$location.path("/myBlog");
			});
	}
	
	$scope.blogList = function(){
		console.log('Inside retrieve all approved blogs function');
		$http.get('http://localhost:8083/ChatMiddleware/showApprovedBlogs')
			.then(function(response) {
				console.log('Inside response');
				console.log(response.data);
				$scope.blogList = response.data;
			});
	}
	
	$scope.allBlogs = function(){
		console.log('Inside show all blogs function');
		$http.get('http://localhost:8083/ChatMiddleware/showApprovedBlogs')
			.then(function(response){
				$scope.allBlogList = response.data;
			})
	}	
	$scope.listBlog();
	$scope.allBlogs();
});