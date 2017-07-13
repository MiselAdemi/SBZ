var services = angular.module('inventory.services', ['ngResource']);

services.service('inventoryService', ['$http', function($http){
	
	this.allArticles = function(){
		return $.ajax({ url: "/SBZProjekat/api/article/all" });
	}
	
	this.fillTheStocks = function(article, count){
		 return $.ajax({ url: "/SBZProjekat/api/article/fillTheStocks/" + article.sifra + "/" + count, 
				type: "POST", 
				contentType: "application/json"
				});
	}
	
	this.getAllBills = function(){
		return $http.get("/SBZProjekat/api/customer/allBills");
	}

	this.approveBill = function(bill){
		return $http.post("/SBZProjekat/api/customer/approveBill", bill);
	}
	
	this.removeBill = function(bill){
		return $http.post("/SBZProjekat/api/customer/removeBill", bill);
	}
	
	this.addArticle = function(article){
		return $http.post("/SBZProjekat/api/article/add", article);
	}

}]);