var services = angular.module('dashboard.services', ['ngResource']);

services.service('dashboardService', ['$http', function($http){
	
	this.getCategories = function() {
		return $http.get("/SBZProjekat/api/articleCategory/all");
	}
	
	this.addNewCategory = function(category) {
		return $http.post("/SBZProjekat/api/articleCategory/new", category);
	}
	
	this.deleteCategory = function(category) {
		return $http.post("/SBZProjekat/api/articleCategory/delete", category);
	}
	
	this.updateCategory = function(category) {
		return $http.post("/SBZProjekat/api/articleCategory/update", category);
	}
	
	////////////////////////
	///// User category/////
	////////////////////////
	
	this.getUserCategories = function() {
		return $http.get("/SBZProjekat/api/userCategory/all");
	}
	
	this.addUserCategory = function(category) {
		return $http.post("/SBZProjekat/api/userCategory/new", category);
	}
	
	this.deleteUserCategory = function(category) {
		return $http.post("/SBZProjekat/api/userCategory/delete", category);
	}
	
	this.updateUserCategory = function(category) {
		return $http.post("/SBZProjekat/api/userCategory/update", category);
	}
	
	////////////////////////
	///// Events ///////////
	////////////////////////
	
	this.getEvents = function() {
		return $http.get("/SBZProjekat/api/event/all");
	}
	
	this.addEvent = function(event) {
		return $http.post("/SBZProjekat/api/event/new", event);
	}
	
	this.deleteEvent = function(event) {
		return $http.post("/SBZProjekat/api/event/delete", event);
	}
	
	this.updateEvent = function(event) {
		return $http.post("/SBZProjekat/api/event/update", event);
	}
	
}]);