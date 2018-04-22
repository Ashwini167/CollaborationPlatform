myApp.controller("ChatController",function($scope, $rootScope, chatService){
	console.log('Starting chat controller');
	
	$scope.messages = [];
	$scope.message = "";
	$scope.max = 140;
	
	$scope.addMessage = function() {
		console.log('Adding Message Method');
		chatService.send($rootScope.currentUser.loginName+":"+$scope.message);
	}
	
	chatService.receive().then(null,null,function(message){
		console.log('Receive Method');
		$scope.messages.push(message);
	})
})