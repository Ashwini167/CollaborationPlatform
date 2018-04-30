myApp.controller("JobController",function($scope, $rootScope, $http, $location,$cookies){
	
	$scope.jobSearch="";
	
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
					$scope.listOfJobs = response.data;
					if($rootScope.currentUser.role=="ROLE_USER"){
						$scope.applicationStatus($scope.listOfJobs);
					}
				});
	}
	
	$scope.appliedJobsByUser = function(){
		console.log('Inside get applied jobs by user function');
		$http.get('http://localhost:8083/ChatMiddleware/viewAppliedJobs/'+$rootScope.currentUser.loginName)
			.then(function(response){
				console.log('Inside jobapplication response');
				$scope.jobApplications = response.data;
				console.log($scope.jobApplications);
		});
	}
	
	$scope.apply = function(jobId){
		console.log('Inside apply function');
		$http.get('http://localhost:8083/ChatMiddleware/applyJob/'+jobId)
				.then(function(response){
					$rootScope.applicationId = response.data;
					console.log('Inside apply job response');
					$location.path('/jobApplied');
				});
	}
	
	$scope.jobList();
	
	$scope.editJob = function(jobId){
		console.log('Inside edit job function');
		$http.get('http://localhost:8083/ChatMiddleware/getJobDetails/'+jobId)
				.then(function(response){
					console.log('Inside the response');
					console.log(response.data);
					$rootScope.jobToEdit = response.data;
					$location.path('/editJob');
				})
	}
	
	$scope.applicationStatus = function(jobList){
		console.log('Inside Check application Status');
		$http.put('http://localhost:8083/ChatMiddleware/applicationStatus',jobList)
			.then(function(response){
				$scope.applied = response.data;
			})
	}
	
	$scope.withdraw = function(jobId){
		console.log('Inside withdraw application function');
		$http.get('http://localhost:8083/ChatMiddleware/withdrawApplication/'+jobId)
				.then(function(response){
					alert('You have successfully withdrawn the job application!')
					$scope.jobList();
					$location.path('/viewJobs');
				});
	}
	
	$scope.updateJob = function(jobId){
		console.log('Inside update function');
		$http.post('http://localhost:8083/ChatMiddleware/updateJob/'+jobId,$rootScope.jobToEdit)
			.then(function(response){
				console.log('Inside update method. Update Success');
				alert('Job details updated successfully');
				$scope.jobList();
				$location.path('/viewJobs');
			})
	}
	
	$scope.deleteJob = function(jobId){
		console.log('Inside delete function');
		$http.get('http://localhost:8083/ChatMiddleware/deleteJob/'+jobId)
			.then(function(response){
				console.log('Inside delete method. Delete Success');
				alert('Job deleted successfully');
				$scope.jobList();
				$location.path('/viewJobs');
			})
	}
	
	$scope.viewAppliedJobs = function() {
		if($rootScope.currentUser.role=="ROLE_USER"){
			$http.get('http://localhost:8083/ChatMiddleware/viewAppliedJobs/'+$rootScope.currentUser.loginName)
					.then(function(response){
						console.log('Inside response');
						console.log(response.data);
						$scope.appliedJobs = response.data;						
					})
		}
	}
	
	$scope.viewAppliedJobs();
})

myApp.filter("statusCheck",function(){
	console.log('Inside filter');
	return function(status){
		switch(status){
		case 'A':
			return "Applied";
		case 'W':
			return "Withdrawn";
		case 'E':
			return "Job Expired";
		}
	}
})