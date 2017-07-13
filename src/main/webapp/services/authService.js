var services = angular.module('auth.services', ['ngResource']);

services.service('authService', ['$http', function($http){

	/*this.logOut = function(){
		return $http.get("/Chat/rest/login");
	}

	this.save = function(guest){
		return $http.put("/Chat/rest/login",guest);
	}

	this.checkRights = function(){
		return $http.get("/login/getLogged");
	}*/

	this.login = function(user){
		return $.ajax({ url: "/SBZProjekat/api/authentication/login", 
			type: "POST", 
			data: JSON.stringify(user), 
			contentType: "application/json"
			});
	}

	this.register = function(user){
		 return $.ajax({ url: "/SBZProjekat/api/authentication/register", 
			type: "POST", 
			data: JSON.stringify(user), 
			contentType: "application/json"
			});
	}
}]);