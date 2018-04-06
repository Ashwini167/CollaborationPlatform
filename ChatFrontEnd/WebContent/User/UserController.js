myApp.controller("UserController",function($scope, $rootScope, $http, $location){
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
		console.log('Inside register function');
		$http.post('http://localhost:8083/ChatMiddleware/addUserDetails',$scope.User)
				.then(function(response){
					console.log('Registration Complete');
					console.log(response.statusText);
					$location.path("/login");
				});
	}
	
	$scope.login = function(){
		$http.post('http://localhost:8083/ChatMiddleware/authenticateUser',$scope.User)
				.then(function(response) {
					$scope.User = response.data;
					$rootScope.currentUser = response.data;
					$location.path("/home");
		});
	}
});