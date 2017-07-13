var services = angular.module('index.services', ['ngResource']);

services.service('indexService', ['$http', function($http){
	
	this.allArticles = function(){
		return $.ajax({ url: "/SBZProjekat/api/article/allActive" });
	}
	
	this.allArticleCategories = function(){
		return $.ajax({ url: "/SBZProjekat/api/articleCategory/all" });
	}
	
	this.refreshUser = function(user){
		return $http.post("/SBZProjekat/api/customer/refresh", user);
	}
	
	this.shoppingHistory = function(user){
		return $http.post("/SBZProjekat/api/customer/shoppingHistory", user);
	}
	
	this.allActionEvents = function(){
		return $http.get("/SBZProjekat/api/article/actionEvents");
	}
	
	this.userUpdate = function(user){
		return $http.post("/SBZProjekat/api/customer/updateUser", user);
	}

}]);