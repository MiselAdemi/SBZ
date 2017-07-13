var app = angular.module('auth.controllers', []);

app.controller('authController', ['$rootScope','$scope','authService', '$location', '$http', '$state', '$window',
  	function ($rootScope, $scope, authService, $location, $http, $state, $window) {
		$scope.base_url = window.location.origin + "/SBZProjekat/#";
	
		init();
		function init(){
			if(sessionStorage.user != undefined){
				$rootScope.current_user = JSON.parse(sessionStorage.user);
				console.log($rootScope.current_user.role);
				if($rootScope.current_user.role=='MANAGER')
					$window.location.href = $scope.base_url + '/index';
				else if($rootScope.current_user.role=='EMPLOYEE')
					$window.location.href = $scope.base_url + '/index';
				else if($rootScope.current_user.role=='CUSTOMER')
					$window.location.href = $scope.base_url + '/index';
			}
		}
		
		$scope.login = function () {  
			authService.login($scope.user).then(
					function(data, textStatus, response){	  
						if(response.status == 401) {
							alert("Wrong username or password!");
							return;
						}else {
							sessionStorage.user = JSON.stringify(data);
							$rootScope.current_user = JSON.parse(sessionStorage.user);
							
							if($rootScope.current_user.role == 'MANAGER') {
								$window.location.href = $scope.base_url + '/index';
							} else if($rootScope.current_user.role == 'EMPLOYEE') {
								$window.location.href = $scope.base_url + '/index';
							} else if($rootScope.current_user.role == 'CUSTOMER'){
								console.log("Redirect");
								$window.location.href = $scope.base_url + '/index';
							}

						}
					},
					function (response, textStatus, errorThrown) {
						console.error("Failed to create a new user!", response);
					}
			);
		};
		
		$scope.register = function () {  			
			authService.register($scope.user).then(
					function(data, textStatus, response){	  
						if(response.status == 202) {
							alert("User with this username already exists!");
						}else {
							$window.location.href = window.location.origin + "/SBZProjekat/#/index";
						}
					},
					function (response, textStatus, errorThrown) {
						console.error("Failed to create a new user!", response);
					}
			);
		};

}]);