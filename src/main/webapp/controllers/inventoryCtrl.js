var app = angular.module('inventory.controllers', []);

app.controller('inventoryController', ['$rootScope','$scope', '$location', '$http', '$state', '$window',
	'inventoryService', 'dashboardService', '$uibModal',
  	function ($rootScope, $scope, $location, $http, $state, $window, inventoryService, dashboardService, $uibModal, buyerService) {
	
	$scope.newArticle = {};

	$scope.init = function(){
		if(sessionStorage.user != undefined){
			$rootScope.current_user = JSON.parse(sessionStorage.user);
		}
		
		if($rootScope.current_user.role=='MANAGER')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		else if($rootScope.current_user.role=='CUSTOMER')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		
		$scope.getAllArticles();
		$scope.getAllBills();
		$scope.getCategories();
	}
	
	$scope.getAllArticles = function() {
		inventoryService.allArticles().then(
				function(data, textStatus, response){	  
					$scope.articles = data;
					$scope.$apply();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all articles!", response);
				}
		);
	}
	
	$scope.getAllBills = function() {
		inventoryService.getAllBills().then(
				function(data, textStatus, response){	  
					$scope.bills = data.data;
					//$scope.$apply();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all bills!", response);
				}
		);
	}
	
	$scope.getCategories = function() {
		dashboardService.getCategories().then(
				function(data, textStatus, response){	  
					$scope.categories = data.data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all categories!", response);
				}
		);
	}

	$scope.approveBill = function(bill) {
		inventoryService.approveBill(bill).then(
				function(data, textStatus, response){	  
					console.log(data);
					if(data.statusText == "OK") {
						$scope.getAllBills();
					}else {
						alert("Cannot finish order, missing articles!");
					}
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refill article!", response);
				}
		);
	}
	
	$scope.removeBill = function(bill) {
		inventoryService.removeBill(bill).then(
				function(data, textStatus, response){	  
					$scope.getAllBills();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refill article!", response);
				}
		);
	}
	
	$scope.fillTheStocks = function(article, count) {
		console.log(count);
		inventoryService.fillTheStocks(article, count).then(
				function(data, textStatus, response){	  
					$scope.getAllArticles();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refill article!", response);
				}
		);
	}
	
	$scope.addArticle = function() {
		if ($scope.newArticle.naziv == null || $scope.newArticle.kategorija == null || $scope.newArticle.cena == null || $scope.newArticle.brojnoStanje == null || $scope.newArticle.statusZapisa == null || $scope.newArticle.minimalnoStanje == null) {
			alert("All fields must be filled!");
			return;
		}
		
		$scope.newArticle.kategorija = JSON.parse($scope.newArticle.kategorija);
		console.log($scope.newArticle);
		
		inventoryService.addArticle($scope.newArticle).then(
				function(data, textStatus, response){	  
					$scope.getAllArticles();
					$('#myModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refill article!", response);
				}
		);
	}
	    
	/////////////////////////////////
	////////// Filtering ////////////
	/////////////////////////////////
	
	$scope.filterOptions = {
			stores: [
				{id : 2, name : 'Show All', stanje: "all" },
				{id : 3, name : 'Confirmed', stanje: "REALIZOVANO" },
				{id : 4, name : 'Pending', stanje: "PORUCENO" },
				{id : 5, name : 'Declined', stanje: "OTKAZANO" }
				]
	}
	
	$scope.filterItem = {
			store: $scope.filterOptions.stores[0]
	}

	$scope.customFilter = function (data) {
		if (data.stanje === $scope.filterItem.store.stanje) {
			return true;
		} else if ($scope.filterItem.store.stanje === "all") {
			return true;
		} else {
			return false;
		}
	 }  
}]);