var services = angular.module('cart.services', ['ngResource']);

services.service('cartService', ['$http', function($http){
	
	this.createBill = function(bill){
		 /*return $.ajax({ url: "/SBZProjekat/api/customer/createBill", 
			type: "POST", 
			data: JSON.stringify(bill), 
			contentType: "application/json"
			});*/
		
		console.log(bill);
		
		return $http.post("/SBZProjekat/api/customer/createBill", bill);
	}
	
	this.buy = function(bill){
		return $http.post("/SBZProjekat/api/customer/buy", bill);
	}

}]);