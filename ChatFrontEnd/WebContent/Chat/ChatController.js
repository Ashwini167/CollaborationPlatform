myApp.controller("ChatController",function($scope, $rootScope, chatService){
	console.log('Starting chat controller');
	console.log('Current user in chat controller is '+$rootScope.currentUser.loginName);
	$scope.messages = [];
	$scope.message = "";
	$scope.max = 140;
	
	$scope.addMessage = function() {
		console.log('Adding Message Method');
		chatService.send($rootScope.currentUser.loginName+" says "+$scope.message);
	}
	
	chatService.receive().then(null,null,function(message){
		console.log('Receive Method');
		$scope.messages.push(message);
	})
})