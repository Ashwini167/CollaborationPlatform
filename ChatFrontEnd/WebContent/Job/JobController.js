myApp.controller("JobController",function($scope, $rootScope, $http, $location,$cookies){
	$scope.job = {
		"jobId":0,
		"jobDesignation":'',
		"company":'',
		"salary":'',
		"location":'',
		"jobDesc":'',
		"skillSet":'',
		"status":'',
		"jobPostedOn":''
	};
	
	$scope.addJob = function(){
		console.log('Inside add job function');
		$http.post('http://localhost:8083/ChatMiddleware/addJob',$scope.job)
				.then(function(response){
					console.log('Added job');
					$location.path('/viewJobs');
				});
	}
	
	$scope.jobList = function(){
		console.log('Inside jobList function');
		$http.get('http://localhost:8083/ChatMiddleware/viewAllJobs')
				.then(function(response){
					console.log('Inside jobList response');
					console.log(response.data);
					$scope.listOfJobs = response.data;
				});
	}
	
	$scope.apply = function(jobId){
		console.log('Inside apply function');
		$http.get('')
				.then(function(response){
					console.log('Inside apply job response');
					$location.path('')	
				});
	}
	
	$scope.jobList();
})
