var app = angular.module('index.controllers', []);

app.controller('indexController', ['$rootScope','$scope', '$location', '$http', '$state', '$window',
	'indexService','$uibModal',
  	function ($rootScope, $scope, $location, $http, $state, $window, indexService, $uibModal) {
	
	$scope.filter = {code: null, name: null, category: null, price_from: null, price_to: null};

	$scope.init = function(){
		if(sessionStorage.user != undefined){
			$rootScope.current_user = JSON.parse(sessionStorage.user);
		}
		
		$scope.getAllArticles();
		$scope.getAllArticleCategories();
		$scope.cart = [];
		
		if(sessionStorage.cart == undefined) {
			$rootScope.cart = new Object();
			$rootScope.cart.articles = [];
			sessionStorage.cart = JSON.stringify($rootScope.cart);
		}
		
		$rootScope.refreshUser();
	}
	
	$rootScope.refreshUser = function() {
		indexService.refreshUser($rootScope.current_user).then(
				function(data, textStatus, response){	  
					$rootScope.current_user = data.data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refresh user!", response);
				}
		);
		
		indexService.shoppingHistory($rootScope.current_user).then(
				function(data, textStatus, response){	  
					$scope.shoppingHistory = data.data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to refresh user!", response);
				}
		);
	}
	
	$scope.getAllArticles = function() {
		indexService.allArticles().then(
				function(data, textStatus, response){	  
					$scope.articles = data;
					$scope.filteredArticles = $scope.articles;
					$scope.getAllActionEvents();
					$scope.$apply();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all articles!", response);
				}
		);
	}
	
	$scope.getAllArticleCategories = function() {
		indexService.allArticleCategories().then(
				function(data, textStatus, response){	  
					$scope.articleCategories = data;
					$scope.$apply();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all article categories!", response);
				}
		);
	}
	
	$scope.articleDiscountList = function() {
		
		for(var i = 0; i < $scope.filteredArticles.length; i++) {
			var a = checkIfActionExists($scope.filteredArticles[i]);
			
			if(a != null) {
				$scope.filteredArticles[i].akcija = a;
			}
		}
		
		console.log($scope.filteredArticles);
	}
	
	function checkIfActionExists(article) {
		var list = [];

		for(var i = 0; i < $scope.actionEvents.length; i++) {
			for(var j = 0; j < $scope.actionEvents[i].listaKategorija.length; j++) {
				if($scope.actionEvents[i].listaKategorija[j] != null && article.kategorija != null && $scope.actionEvents[i].listaKategorija[j].sifra == article.kategorija.sifra) {
					list.push($scope.actionEvents[i]);
					break;
				}
			}
		}
		
		var best = null;
		
		if(list.length > 0) {
			best = list[0];
			
			for(var i = 1; i < list.length; i++) {
				if(list[i].popust > best.popust) {
					best = list[i];
				}
			}
		}

		return best;
	}
	
	$scope.filterTable = function() {
		$scope.filteredArticles = [];
		
		if($scope.filter.code != null) {
			if($scope.filteredArticles.length > 0) {
				for(var i = 0; i < $scope.filteredArticles.length; i++) {
					if($scope.filteredArticles[i].sifra != $scope.filter.code) {
						var index = $scope.filteredArticles.indexOf($scope.filteredArticles[i]);
						$scope.filteredArticles.splice(index, 1);
					}
				}
			}else {
				for(var i = 0; i < $scope.articles.length; i++) {
					if($scope.articles[i].sifra == $scope.filter.code) {
						$scope.filteredArticles.push($scope.articles[i]);
					}
				}
			}
		}
		
		if($scope.filter.name != null) {
			if($scope.filteredArticles.length > 0) {
				for(var i = 0; i < $scope.filteredArticles.length; i++) {
					if(!$scope.filteredArticles[i].naziv.toLowerCase().startsWith($scope.filter.name.toLowerCase())) {
						var index = $scope.filteredArticles.indexOf($scope.filteredArticles[i]);
						$scope.filteredArticles.splice(index, 1);
					}
				}
			}else {
				for(var i = 0; i < $scope.articles.length; i++) {
					if($scope.articles[i].naziv.toLowerCase().startsWith($scope.filter.name.toLowerCase())) {
						$scope.filteredArticles.push($scope.articles[i]);
					}
				}
			}
		}
		
		if($scope.filter.category != null) {
			if($scope.filteredArticles.length > 0) {
				for(var i = 0; i < $scope.filteredArticles.length; i++) {
					if($scope.filteredArticles[i].kategorija.sifra != $scope.filter.category) {
						var index = $scope.filteredArticles.indexOf($scope.filteredArticles[i]);
						$scope.filteredArticles.splice(index, 1);
					}
				}
			}else {
				for(var i = 0; i < $scope.articles.length; i++) {
					if($scope.articles[i].kategorija.sifra == $scope.filter.category) {
						$scope.filteredArticles.push($scope.articles[i]);
					}
				}
			}
		}
		
		if($scope.filter.price_from != null) {
			if($scope.filteredArticles.length > 0) {
				for(var i = 0; i < $scope.filteredArticles.length; i++) {
					if($scope.filteredArticles[i].cena < $scope.filter.price_from) {
						var index = $scope.filteredArticles.indexOf($scope.filteredArticles[i]);
						$scope.filteredArticles.splice(index, 1);
					}
				}
			} else {
				for(var i = 0; i < $scope.articles.length; i++) {
					if($scope.articles[i].cena >= $scope.filter.price_from) {
						$scope.filteredArticles.push($scope.articles[i]);
					}
				}
			}
			
		}
		
		if($scope.filter.price_to != null) {
			if($scope.filteredArticles.length > 0) {
				for(var i = 0; i < $scope.filteredArticles.length; i++) {
					if($scope.filteredArticles[i].cena > $scope.filter.price_to) {
						var index = $scope.filteredArticles.indexOf($scope.filteredArticles[i]);
						$scope.filteredArticles.splice(index, 1);
					}
				}
			} else {
				for(var i = 0; i < $scope.articles.length; i++) {
					if($scope.articles[i].cena <= $scope.filter.price_to) {
						$scope.filteredArticles.push($scope.articles[i]);
					}
				}
			}

		}

	}
    
	$scope.resetFilterTable = function() {
		$scope.filter.code = null;
		$scope.filter.name = null;
		$scope.filter.category = null;
		$scope.filter.price_from = null;
		$scope.filter.price_to = null;
		$scope.filteredArticles = $scope.articles;
	}
	
	$scope.checkAmount = function(article, amount) {
		if(amount < 1 || amount > article.brojnoStanje) {
			$("#amount-" + article.sifra).val('');
		}
		
		console.log($("#amount-" + article.sifra).val());
		
		//$scope.amount = $("#amount-" + article.sifra).val();
	}
	
	$scope.addToCart = function(article, amount) {
		console.log(amount);
		$rootScope.cart = JSON.parse(sessionStorage.cart);
		
		console.log("cart");
		console.log($rootScope.cart);
		
		if(!$scope.checkIfAmountExists(article, amount)) {
			return;
		}
		
		$scope.checkIfExists(article, amount);
		
		console.log("cart");
		console.log($rootScope.cart);
		
		sessionStorage.cart = JSON.stringify($rootScope.cart);
		$("#amount-" + article.sifra).val('');
	}
	    
	$scope.checkIfAmountExists = function(article, amount) {
		for(var i = 0; i < $rootScope.cart.articles.length; i++){
			if($rootScope.cart.articles[i].sifra == article.sifra){
				if(($rootScope.cart.articles[i].amount + amount) > article.brojnoStanje) {
					alert("There is no enought amount of this article!");
					return false;
				}
			}
		}
		
		return true;
	}
	
	$scope.checkIfExists = function(article, amount) {
		console.log("----exists----");
		console.log(amount);
		console.log(article);
		if(amount != null) {
			for(var i = 0; i < $rootScope.cart.articles.length; i++){
				if($rootScope.cart.articles[i].sifra == article.sifra){
					$rootScope.cart.articles[i].amount += amount;
					return;
				}
			}
			$rootScope.cart.articles.push(article);
			$rootScope.cart.articles[$rootScope.cart.articles.length-1].amount = amount;
		}
		
		console.log("---------------");
	}
	
	$scope.getAllActionEvents = function() {
		indexService.allActionEvents().then(
				function(data, textStatus, response){
					$scope.actionEvents = data.data;
					$scope.articleDiscountList();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all articles!", response);
				}
		);
	}
	
	$scope.updateUserFun = function(updateUser) {
		$rootScope.current_user.first_name = updateUser.first_name;
		$rootScope.current_user.last_name = updateUser.last_name;
		
		indexService.userUpdate($rootScope.current_user).then(
				function(data, textStatus, response){
					$window.location.reload();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all articles!", response);
				}
		);
	}

}]);