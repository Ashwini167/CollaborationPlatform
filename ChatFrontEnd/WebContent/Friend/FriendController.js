myApp.controller("FriendController",function($scope, $rootScope, $http, $location){
	
	$scope.suggestFriends = function(){
		console.log('Inside suggestFriends function');
		$http.get('http://localhost:8083/ChatMiddleware/showSuggestedFriends')
			.then(function(response) {
				console.log('Friend suggestions received');
				console.log(response.data);
				$scope.suggestedFrndList = response.data;
			});
	}
	
	$scope.addFriend = function(loginName){
		console.log('Inside add friend method');
		$http.get('http://localhost:8083/ChatMiddleware/sendFriendRequest/'+loginName)
				.then(function(response){
					console.log('Friend request sent');
					alert('Your friend request was sent successfully!');
					$scope.suggestFriends();
					$location.path('/friendsPage');
				})
	}
	
	$scope.pendingFrnds = function(){
		console.log('Inside add friend method');
		$http.get('http://localhost:8083/ChatMiddleware/viewPendingRequests')
			.then(function(response){
				console.log('Friend requests fetched');
				$scope.pendingRequests = response.data;
				console.log(response.data);
		})
	}
	
	$scope.acceptFriend = function(friendId){
		console.log('Inside accept friend with friendId '+friendId);
		$http.put('http://localhost:8083/ChatMiddleware/acceptFriend',friendId)
			.then(function(response){
				console.log('Friend Request Accepted');
				alert('You have accepted the friend request!');
				$scope.pendingFrnds();
				$location.path('/pendingList');
			})		
	}
	
	$scope.rejectFriend = function(friendId){
		console.log('Inside reject friend with friendId '+friendId);
		$http.put('http://localhost:8083/ChatMiddleware/rejectFriend',friendId)
			.then(function(response){
				console.log('Friend Request Rejected');
				alert('You have rejected the friend request!');
				$scope.pendingFrnds();
				$location.path('/pendingList');
			})		
	}
	
	$scope.viewFriends = function(){
		console.log('Inside viewfriends method');
		$http.get('http://localhost:8083/ChatMiddleware/viewFriends')
			.then(function(response){
				console.log('Response obtained - viewFriends function');
				$scope.friendList = response.data;
			})
	}
	
	$scope.viewFriends();
	$scope.pendingFrnds();
	$scope.suggestFriends();
})