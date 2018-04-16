myApp.controller("BlogCommentController",function($scope, $rootScope, $http, $location){

	$scope.blogComment = {
			"commentId":0,"commentText":'', "commentDate":'',"status":'',
			"blog" : {"blogId":0, "blogName":'', "blogContent":'', "createdDate":'', "likes":0, "status":'',
						"userDetail":{
							"loginName":'',
							"password":'',
							"username":'',
							"emailId":'',
							"mobileNo":'',
							"address":'',
							"role":'',
							"enabled":''				
						}
			},
			"userDetail":{
				"loginName":'',
				"password":'',
				"username":'',
				"emailId":'',
				"mobileNo":'',
				"address":'',
				"role":'',
				"enabled":''				
			}
	};	

	$scope.addComment = function(){
		console.log('Inside add comment function');
		$scope.blogComment.blog = $rootScope.readBlog;
		$http.post('http://localhost:8083/ChatMiddleware/addBlogComment',$scope.blogComment)
			.then(function(response) {
				console.log('Blog Id is: '+$rootScope.readBlog.blogId);
				console.log(response.data);
				$scope.getComments($rootScope.readBlog.blogId);
				$scope.blogComment ='';
				$location.path("/readBlog");
			});
	}

});