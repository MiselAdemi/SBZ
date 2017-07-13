angular.module('app', ['ui.router','ui.bootstrap','index.controllers', 'index.services','auth.controllers','auth.services', 'inventory.controllers', 'inventory.services', 'cart.controllers', 'cart.services', 'dashboard.controllers', 'dashboard.services']).config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/index');

	$stateProvider
	.state('index', {
		url : '/index',
		templateUrl : 'pages/index.html',
		controller : 'indexController'
	})
	.state('login', {
		url : '/login',
		templateUrl : 'pages/login.html',
		controller : 'authController'
	})
	.state('register', {
		url : '/register',
		templateUrl : 'pages/register.html',
		controller : 'authController'
	})
	.state('inventory', {
		url : '/inventory',
		templateUrl : 'pages/inventory.html',
		controller : 'inventoryController'
	})
	.state('cart', {
		url : '/cart',
		templateUrl : 'pages/cart.html',
		controller : 'cartController'
	})
	.state('dashboard', {
		url : '/dashboard',
		templateUrl : 'pages/dashboard.html',
		controller : 'dashboardController'
	})
	.state('profile', {
		url : '/profile',
		templateUrl : 'pages/profile.html',
		controller : 'indexController'
	})

})
.run(function($rootScope, $window) {
	$rootScope.logout = function () {  			
		sessionStorage.removeItem("user");
		sessionStorage.clear();
		$rootScope.current_user = undefined;
		$window.location.href = window.location.origin + "/SBZProjekat/#/index";
	};
});