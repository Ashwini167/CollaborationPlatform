myApp.controller("BlogController",function($scope, $rootScope, $http, $location){
	console.log('Inside blogController');
	$scope.displaySelector = "";
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
			}
	};

	$scope.listBlog = function(){
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
	
	$scope.allBlogs = function(){
		console.log('Inside show all blogs function');
		$http.get('http://localhost:8083/ChatMiddleware/showApprovedBlogs')
			.then(function(response){
				$scope.allBlogList = response.data;
			})
	}
	
	$scope.blogListForAdmin = function(){
		console.log('Inside show all blogs function');
		$http.get('http://localhost:8083/ChatMiddleware/showAllBlogs')
			.then(function(response){
				$scope.completeBlogList = response.data;
			})
	}
	
	$scope.listBlog();
	$scope.allBlogs();
	$scope.blogListForAdmin();
	
	$scope.readMore = function(blogId){
		console.log('Inside read more function');
		$http.get('http://localhost:8083/ChatMiddleware/getBlog/'+blogId)
			.then(function(response) {
				console.log('Inside readBlog');
				$scope.getComments(blogId);
				$rootScope.readBlog = response.data;
				$rootScope.likes = $rootScope.readBlog.likes;
				console.log('Likes are: '+$rootScope.likes);
				$location.path("/readBlog");
			});
	}
	
	$scope.incrementLikes = function(blogId) {
		console.log('Inside like method');
		$http.get('http://localhost:8083/ChatMiddleware/incrementLikes/'+blogId)
			.then(function(response){
				console.log('after response');
				$rootScope.likes++;
				console.log('After increment, likes are: '+$rootScope.likes);
			});
	}
	
	$scope.getComments = function(blogId){
		console.log('Inside get blog of blogId: '+blogId);
		$http.get('http://localhost:8083/ChatMiddleware/getBlogComments/'+blogId)
				.then(function(response){
					console.log('After rest controller response');
					$rootScope.commentList = response.data;
				});
	}
	
	$scope.approveBlog = function(blogId){
		console.log('Inside approve blog function: '+blogId);
		$http.get('http://localhost:8083/ChatMiddleware/approveBlog/'+blogId)
		.then(function(response){
			console.log('Blog Approved');
			$scope.blogListForAdmin();
			alert('The blog is approved!');
			location.path('/manageBlogs');
		});
	}
	
	$scope.rejectBlog = function(blogId){
		console.log('Inside reject blog function: '+blogId);
		$http.get('http://localhost:8083/ChatMiddleware/rejectBlog/'+blogId)
		.then(function(response){
			console.log('Blog Rejected');
			$scope.blogListForAdmin();
			alert('The blog is rejected!');
			location.path('/manageBlogs');
		});
	}
	
	$scope.deleteBlog = function(blogId){
		console.log('Inside delete blog function: '+blogId);
		$http.get('http://localhost:8083/ChatMiddleware/deleteBlog/'+blogId)
		.then(function(response){
			console.log('Blog Deleted');
			alert('Blog deleted successfully!');
			$scope.listBlog();
			$location.path('/myBlog');
		});
	}
	
	$scope.editBlog = function(blogId){
		console.log('To edit blog: '+blogId);
		$http.get('http://localhost:8083/ChatMiddleware/getBlog/'+blogId)
			.then(function(response) {
			console.log('Inside show blog to update');
			$rootScope.blogUpdate = response.data;
			$location.path("/editBlog");
		});
	}
	
	$scope.updateBlog = function(blogId){
		console.log('Inside update blog function: '+blogId);
		$http.post('http://localhost:8083/ChatMiddleware/updateBlog/'+blogId,$rootScope.blogUpdate)
		.then(function(response){
			console.log('Blog updated');
			alert('Blog updated successfully!');
			$scope.listBlog();
			$location.path('/myBlog');
		});
	}
	
	$scope.sortComment = function(comment) {
		console.log('Inside sort comment '+comment.commentDate);
		return new Date(comment.commentDate);
	}
});		