window.addEventListener("load", function () {
	
	$( "#btnAddNewUser" ).click(function() {

		var username = 	$( "#txtUsername" ).val();
		var password = $("#txtPassword").val();
		
		var JSONObject= {"korisnickoIme": username, "sifra": password }; 


		$.ajax({ url: "api/user", 
		type: "POST", 
		data: JSON.stringify(JSONObject), 
		contentType: "application/json",
		success: function(data, textStatus, response){
				
  
		console.log("New user recieved username: " + response.getResponseHeader('Location'));
		   },
		error: function (response, textStatus, errorThrown) {
		console.error("Failed to create a new user!", response);
		}});
		  
	});
	
	
	$( "#btnLogIn" ).click(function() {

		var username = 	$( "#txtUsername" ).val();
		var password = $("#txtPassword").val();
		
		var JSONObject= {"username": username, "password": password }; 


		$.ajax({ url: "api/authentication/logIn", 
		type: "POST", 
		data: JSON.stringify(JSONObject), 
		contentType: "application/json",
		success: function(data, textStatus, response){
				console.log(data.korisnickoIme);
  		   },
		error: function (response, textStatus, errorThrown) {
		console.error("Failed to log in");
		}});
		  
	});
	
	
});

