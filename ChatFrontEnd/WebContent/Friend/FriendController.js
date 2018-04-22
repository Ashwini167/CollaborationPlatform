myApp.controller("FriendController",function($scope, $rootScope, $http, $location){
	
	$scope.suggestFriends = function(){
		console.log('Inside suggestFriends function');
		$http.get('http://localhost:8083/ChatMiddleware/showSuggestedFriends')
			.then(function(response) {
				console.log('Inside response');
				console.log(response.data);
				$scope.suggestedFrndList = response.data;
			});
	}
	
	$scope.suggestFriends();
})