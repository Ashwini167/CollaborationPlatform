myApp.controller("BlogController",function($scope, $rootScope, $http, $location){
	console.log('Inside blogController');
	$scope.listBlog = function(){
		console.log('Inside retrieve user blog function');
		$http.get('http://localhost:8083/ChatMiddleware/showAllBlogsOfUser')
			.then(function(response) {
				console.log('Inside response');
				console.log(response.data);
				$scope.blogData = response.data;
			});
	}	
	$scope.listBlog();
});