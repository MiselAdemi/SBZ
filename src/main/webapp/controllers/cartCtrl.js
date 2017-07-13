var app = angular.module('cart.controllers', []);

app.controller('cartController', ['$rootScope','$scope', '$location', '$http', '$state', '$window', 'cartService', 'indexService', '$uibModal',
  	function ($rootScope, $scope, $location, $http, $state, $window, cartService, indexService, $uibModal) {

	$scope.init = function(){
		if(sessionStorage.user != undefined){
			$rootScope.current_user = JSON.parse(sessionStorage.user);
		}
		
		if($rootScope.current_user.role=='MANAGER')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		else if($rootScope.current_user.role=='EMPLOYEE')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		
		$scope.getAllArticles();
		
		if(sessionStorage.cart == undefined) {
			$rootScope.cart = new Object();
			$rootScope.cart.articles = [];
			sessionStorage.cart = JSON.stringify($rootScope.cart);
		}else {
			$rootScope.cart = JSON.parse(sessionStorage.cart);
		}
	}
	
	$scope.getAllArticles = function() {
		indexService.allArticles().then(
				function(data, textStatus, response){	  
					$scope.articles = data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all articles!", response);
				}
		);
	}
	
	$scope.removeFromCart = function(article){
		$rootScope.cart = JSON.parse(sessionStorage.cart);
		
		for(var i = 0; i < $rootScope.cart.articles.length; i++){
			if($rootScope.cart.articles[i].sifra == article.sifra){
				$rootScope.cart.articles.splice(i,1);
				break;
			}
		}
		
		sessionStorage.cart = JSON.stringify($rootScope.cart);
	}
	
	// Create bill
	$scope.createBill = function(){
		
		var orderList = $scope.createOrderList();
		var bill = new Object();
		bill.datum = new Date();
		bill.kupac = $rootScope.current_user;
		bill.listaStavki = orderList;
		bill.originalnaCena = 0;
		
		for( var i = 0; i < bill.listaStavki.length; i++) {
			bill.originalnaCena += bill.listaStavki[i].originalnaUkupnaCenaStavke;
		}

		cartService.createBill(bill).then(function(response) {
			console.log(response);
			$scope.preparedBill = response.data;
			
			// Update bill
			$scope.bill = bill;
			$scope.bill.konacnaCena = $scope.preparedBill.konacnaCena;
			$scope.bill.brojPotrosenihBodova = $scope.preparedBill.brojPotrosenihBodova;
			//$scope.bill.brojOstvarenihBodova = $scope.preparedBill.brojOstvarenihBodova;
			$scope.bill.procenatUmanjenja = $scope.preparedBill.procenatUmanjenja;

			console.log($scope.bill);
			console.log($scope.preparedBill);
			
			$("#orderDetailModal").modal('show');
		});
	}
	
	$scope.buy = function() {
		
		cartService.buy($scope.bill).then(function(response) {			
			$rootScope.cart.articles = [];
			sessionStorage.cart = JSON.stringify($rootScope.cart);
			$("#orderDetailModal").modal('hide');
			
			setTimeout(function () {
				$window.location.href = window.location.origin + "/SBZProjekat/#/index";
			}, 1000);
		});
	}
	
	// Populate order list
	$scope.createOrderList = function() {
		var orderList = []
		
		for(var i = 0; i < $rootScope.cart.articles.length; i++) {
			var orderArticle = new Object();
			
			orderArticle.redniBroj = i;
			orderArticle.cena = $rootScope.cart.articles[i].cena;
			orderArticle.kolicina = $rootScope.cart.articles[i].amount;
			orderArticle.originalnaUkupnaCenaStavke = $rootScope.cart.articles[i].cena * $rootScope.cart.articles[i].amount;
			
			for(var j = 0; j < $scope.articles.length; j++) {
				if($scope.articles[j].sifra == $rootScope.cart.articles[i].sifra) {		
					orderArticle.artikal = $scope.articles[j];
					orderArticle.artikalSifra = $scope.articles[j].sifra;
				}
			}
			
			orderList.push(orderArticle);
		}
		
		return orderList;
	}
	    
}]);