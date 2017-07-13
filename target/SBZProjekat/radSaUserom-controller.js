/**
 * Code that performs business logic on the page
 * 
 * Listeners/event handlers are attached to appropriate elements as soon as this
 * script is loaded
 * 
 */

// WAIT UNTIL THE WHOLE PAGE, including jquery, IS LOADED AND READY
window.addEventListener("load", function() {

	$("#usereDodaj").click(function() {
		$("#useriSviContainer").empty();

		$.ajax({

			url : "api/radSaUserom/add",
			success : function(data, textStatus, response) {

			},
			error : function(response, textStatus, errorThrown) {
				console.error("Failed to create a new book!", response);
			}
		});

	});

	$("#userePrikazi").click(function() {
		$("#useriSviContainer").empty();

		$.ajax({

			url : "api/radSaUserom/all",
			success : function(data, textStatus, response) {
				console.log("CAOOO!1");

				var userContainer = $('#useriSviContainer');

				console.log("CAOOO!2");
				console.log(data.length);
				for (var i = 0; i < data.length; i++) {
					console.log("CAOOO!3");

					console.log("BROJ USERA: " + data.length);
					var userDiv = document.createElement("div");

					userDiv.style.backgroundColor = "yellow";
					userDiv.innerHTML = "USERNAME:" + data[i].korisnickoIme;
					console.log(userDiv.innerHTML);
					userContainer.append(userDiv);
				}
			},
			error : function(response, textStatus, errorThrown) {
				console.error("Failed to create a new user!", response);
			}
		});
	});

	$("#usereObrisi").click(function() {
		$("#useriSviContainer").empty();

		$.ajax({

			url : "api/radSaUserom/removeAll",

			success : function(data, textStatus, response) {
				console.log("BROJ Korisnika: " + data.length);

			},
			error : function(response, textStatus, errorThrown) {
				console.error("Failed to create a new user!", response);
			}
		});
	});

	$("#useraPronadji").click(function() {
		$("#useriSviContainer").empty();
		console.log("DA VIDIMO");

		$.ajax({

			url : "api/radSaUserom/pretraga",
			success : function(data, textStatus, response) {
				console.log("CAOOO!1");

				var userContainer = $('#useriSviContainer');

				console.log("CAOOO!2");

				var userDiv = document.createElement("div");

				userDiv.style.backgroundColor = "yellow";
				userDiv.innerHTML = "USERNAME:" + data.korisnickoIme;
				console.log(userDiv.innerHTML);
				userContainer.append(userDiv);

			},
			error : function(response, textStatus, errorThrown) {
				console.error("Failed to create a new user!", response);
			}
		});

	});

	// everything is done inside these brackets
});
