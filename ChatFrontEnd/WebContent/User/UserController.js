myApp.controller("UserController",function($scope, $rootScope, $http, $location,$cookies){
	$scope.User = {
			"loginName":'',
			"password":'',
			"username":'',
			"emailId":'',
			"mobileNo":'',
			"address":'',
			"role":'',
			"enabled":''
	};
	
	$scope.register = function(){
		$http.post('http://localhost:8083/ChatMiddleware/addUserDetails',$scope.User)
				.then(function(response){
					$location.path("/login");
				});
	}
	
	$scope.login = function(){
		$http.post('http://localhost:8083/ChatMiddleware/authenticateUser',$scope.User)
				.then(function(response) {
					$scope.User = response.data;
					$rootScope.currentUser = response.data;
					$cookies.putObject("userDetails",response.data);
					$location.path("/home");
		});
	}
	
	$rootScope.logout = function(){
		console.log('Inside logout');
		$cookies.remove("userDetails");
		delete $rootScope.currentUser;
		$location.path("/logout");
	}
	
	$scope.uploadPic = function(){
		
	}
});