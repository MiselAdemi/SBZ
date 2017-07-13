var app = angular.module('dashboard.controllers', []);

app.controller('dashboardController', ['$rootScope','$scope', '$location', '$http', '$state', '$window',
	'dashboardService','$uibModal',
  	function ($rootScope, $scope, $location, $http, $state, $window, dashboardService, $uibModal) {

	$scope.init = function(){
		if(sessionStorage.user != undefined){
			$rootScope.current_user = JSON.parse(sessionStorage.user);
		}
		
		if($rootScope.current_user.role=='CUSTOMER')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		else if($rootScope.current_user.role=='EMPLOYEE')
			$window.location.href = $window.location.origin + "/SBZProjekat/#/index";
		
		$scope.getCategories();
		$scope.getUserCategories();
		$scope.getEvents();
		
		$scope.newCategory = {sifra: "", naziv: "", maksimalniPopust: 0, nadkategorija: null}
		$scope.newUserCategory = {sifra: "", naziv: "", pragoviPotrosnje: []}
		$scope.newEvent = {sifra: "", naziv: "", pocetak: "", zavrsetak: "", listaKategorija: []}
		$scope.tresholds = [];
	}
	
	$scope.getCategories = function() {
		dashboardService.getCategories().then(
				function(data, textStatus, response){	  
					$scope.categories = data.data;
					$scope.filterParentCategories();
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all categories!", response);
				}
		);
	}
	
	$scope.getUserCategories = function() {
		dashboardService.getUserCategories().then(
				function(data, textStatus, response){	  
					$scope.userCategories = data.data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all user categories!", response);
				}
		);
	}
	
	$scope.filterParentCategories = function() {
		$scope.parentCategories = [];
		for(var i = 0; i < $scope.categories.length; i++) {
			if($scope.categories[i].nadkategorija == null) {
				$scope.parentCategories.push($scope.categories[i]);
			}
		}
	}
	
	$scope.editCategoryModalShow = function(category) {
		var modal = $('#categoryModal');
		
		$scope.editCategory = category;
		//$scope.editCategory.maksimalniPopust = category.maksimalniPopust;

		// and finally show the modal
		modal.modal({ show: true });
	}
	
	$scope.updateCategory = function() {
		console.log($scope.editCategory);
		dashboardService.updateCategory($scope.editCategory).then(
				function(data, textStatus, response){	  
					console.log(data);
					$scope.getCategories();
					$('#categoryModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to update category!", response);
				}
		);
	}

	$scope.addCategory = function() {
		if($scope.newCategory.sifra == "" || $scope.newCategory.naziv == "" || $scope.newCategory.maksimalniPopust == null) {
			alert("Must fill everything except category!");
			return;
		}
		
		if($scope.newCategory.nadkategorija != null) {
			$scope.newCategory.nadkategorija = JSON.parse($scope.newCategory.nadkategorija);
		}
		
		console.log($scope.newCategory);
		
		dashboardService.addNewCategory($scope.newCategory).then(
				function(data, textStatus, response){	  
					console.log(data);
					$scope.categories.push($scope.newCategory);
					$('#newCategoryModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to add new category!", response);
				}
		);
	}
	
	$scope.deleteCategory = function(category) {
		console.log(category);
		
		dashboardService.deleteCategory(category).then(
				function(data, textStatus, response){	  
					for(var i = 0; i < $scope.categories.length; i++) {
						if($scope.categories[i].sifra == category.sifra) {
							$scope.categories.splice(i, 1);
						}
					}
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to delete category!", response);
				}
		);
	}
	
	/////////////////////////////
	///// User category /////////
	/////////////////////////////
	
	$scope.addUserCategory = function() {
		if($scope.newUserCategory.sifra == "" || $scope.newUserCategory.naziv == "") {
			alert("Must fill everything!");
			return;
		}
		console.log($scope.newUserCategory);
		
		dashboardService.addUserCategory($scope.newUserCategory).then(
				function(data, textStatus, response){	  
					console.log(data);
					$scope.userCategories.push($scope.newUserCategory);
					$('#newUserCategoryModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to add new user category!", response);
				}
		);
	}
	
	$scope.editUserCategoryModalShow = function(category) {
		var modal = $('#userCategoryModal');
		
		$scope.userCategory = category;

		// and finally show the modal
		modal.modal({ show: true });
	}
	
	$scope.deleteUserCategory = function(category) {
		dashboardService.deleteUserCategory(category).then(
				function(data, textStatus, response){	  
					for(var i = 0; i < $scope.userCategories.length; i++) {
						if($scope.userCategories[i].sifra == category.sifra) {
							$scope.userCategories.splice(i, 1);
						}
					}
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to delete category!", response);
				}
		);
	}
	
	$scope.updateUserCategory = function() {
		console.log($scope.userCategory);
		dashboardService.updateUserCategory($scope.userCategory).then(
				function(data, textStatus, response){	  
					$scope.getUserCategories();
					$('#userCategoryModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to update user category!", response);
				}
		);
	}
	
	$scope.addTreshold = function() {
		if($scope.newTreshold.donjaGranica == null || $scope.newTreshold.gornjaGranica == null || $scope.newTreshold.procenat == null) {
			alert("Must fill everything!");
			return;
		}
		
		$scope.newUserCategory.pragoviPotrosnje.push({id: "", donjaGranica: $scope.newTreshold.donjaGranica, gornjaGranica: $scope.newTreshold.gornjaGranica, procenat: $scope.newTreshold.procenat});
		
		$scope.newTreshold.donjaGranica = "";
		$scope.newTreshold.gornjaGranica = "";
		$scope.newTreshold.procenat = "";
	}
	
	$scope.addTresholdEdit = function() {
		if($scope.newTreshold.donjaGranica == null || $scope.newTreshold.gornjaGranica == null || $scope.newTreshold.procenat == null) {
			alert("Must fill everything!");
			return;
		}
		
		$scope.userCategory.pragoviPotrosnje.push({id: "", donjaGranica: $scope.newTreshold.donjaGranica, gornjaGranica: $scope.newTreshold.gornjaGranica, procenat: $scope.newTreshold.procenat});
		
		$scope.newTreshold.donjaGranica = "";
		$scope.newTreshold.gornjaGranica = "";
		$scope.newTreshold.procenat = "";
	}
	

	$scope.deleteTreshold = function(index, flag) {
		if(flag == 'new') {
			$scope.newUserCategory.pragoviPotrosnje.splice(index - 1, 1);
		}else if(flag == 'edit') {
			$scope.userCategory.pragoviPotrosnje.splice(index - 1, 1);
		}
	}
	
	////////////////////////////
	///// Events ///////////////
	////////////////////////////
	
	$scope.getEvents = function() {
		dashboardService.getEvents().then(
				function(data, textStatus, response){	  
					$scope.events = data.data;
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to fetch all events!", response);
				}
		);
	}
	
	$scope.deleteEvent = function(event) {
		dashboardService.deleteEvent(event).then(
				function(data, textStatus, response){	  
					for(var i = 0; i < $scope.events.length; i++) {
						if($scope.events[i].sifra == event.sifra) {
							$scope.events.splice(i, 1);
						}
					}
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to delete event!", response);
				}
		);
	}
	
	$scope.addEvent = function() {
		console.log($scope.newEvent);
		
		dashboardService.addEvent($scope.newEvent).then(
				function(data, textStatus, response){	  
					$scope.events.push($scope.newEvent);
					$('#newEventModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to add event!", response);
				}
		);
	}
	
	$scope.addEventCategory = function(type) {
		$scope.newEventCategoryParse = JSON.parse($scope.newEventCategory);
		
		if(type == 'new') {
			for(var i = 0; i < $scope.newEvent.listaKategorija.length; i++) {
				if($scope.newEvent.listaKategorija[i].sifra == $scope.newEventCategoryParse.sifra) {
					return;
				}
			}
			
			$scope.newEvent.listaKategorija.push({sifra: $scope.newEventCategoryParse.sifra, naziv: $scope.newEventCategoryParse.naziv, nadkategorija: $scope.newEventCategoryParse.nadkategorija, maksimalniPopust: $scope.newEventCategoryParse.maksimalniPopust })
		
			console.log($scope.newEvent);
		}else if(type == 'edit') {
			for(var i = 0; i < $scope.editEvent.listaKategorija.length; i++) {
				if($scope.editEvent.listaKategorija[i].sifra == $scope.newEventCategoryParse.sifra) {
					return;
				}
			}
			
			$scope.editEvent.listaKategorija.push({sifra: $scope.newEventCategoryParse.sifra, naziv: $scope.newEventCategoryParse.naziv, nadkategorija: $scope.newEventCategoryParse.nadkategorija, maksimalniPopust: $scope.newEventCategoryParse.maksimalniPopust })
			
		}
	}
	
	
	$scope.deleteEventCategory = function(index, type) {
		if(type == 'new') {
			$scope.newEvent.listaKategorija.splice(index - 1, 1);
		}else if(type == 'edit') {
			$scope.editEvent.listaKategorija.splice(index - 1, 1);
		}
	}
	
	$scope.editEventModalShow = function(event) {
		var modal = $('#editEventModal');
		
		$scope.editEvent = event;

		// and finally show the modal
		modal.modal({ show: true });
	}
	
	$scope.updateEvent = function() {
		console.log($scope.editEvent);
		
		dashboardService.updateEvent($scope.editEvent).then(
				function(data, textStatus, response){	  
					$scope.getEvents();
					$('#editEventModal').modal('hide');
				},
				function (response, textStatus, errorThrown) {
					console.error("Failed to update event!", response);
				}
		);
	}

}]);