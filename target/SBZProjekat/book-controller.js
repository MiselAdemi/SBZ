/** Code that performs business logic on the page 

Listeners/event handlers are attached to appropriate elements as soon as this script is loaded

*/

// WAIT UNTIL THE WHOLE PAGE, including jquery,  IS LOADED AND READY 
window.addEventListener("load", function () {

	 /*****************************************************************************
	 * Mogu se i ovako dobaviti pa registrovati eventHandleri sa
	 *       .addEventListener("click", function(), 
	 *       
	 *       Ali kad se vec ima jQuery...
	 * 
    
		var btnAddNewBook = document.getElementById("btnAddNewBook");
		var btnFetchAllBooks = document.getElementById("btnFetchAllBooks");
		var txtBookName = document.getElementById("txtBookName");
		var divBookContainer = document.getElementById("allBooksContainer");
	 ******************************************************************************/
	
	$( "#btnAddNewBook" ).click(function() {
		$("#allBooksContainer").empty();
		var bookName = 	$( "#txtBookName" ).val();
		  var JSONObject= {"name": bookName }; 


		  $.ajax({ url: "api/v1/book", 
		  type: "POST", 
		  data: JSON.stringify(JSONObject), 
		  contentType: "application/json",
		  success: function(data, textStatus, response){
				
  
			  console.log("New book received the id:" + response.getResponseHeader('Location'));
				console.log("SAVA STRINA");

		   },
		   error: function (response, textStatus, errorThrown) {
			   console.error("Failed to create a new book!", response);
		   }});
		  
	});
	
	$( "#btnFetchAllBooks" ).click(function() {
		$("#allBooksContainer").empty();

			$.ajax({

		        url: "api/v1/book/all",
		        success: function(data, textStatus, response) {
				console.log("CAOOO!2");

		        	var  bookContainer = $('#allBooksContainer');
			    	for(var i =0; i < data.length; i++) {
			    		console.log("BROJ KNJIGA: "+ data.length);
			    		var bookDiv = document.createElement("div");
			    		
			    		bookDiv.style.backgroundColor = "yellow";
			    		bookDiv.innerHTML = "Book id:" + data[i].id  +  ", book name:" + data[i].name;
			    		console.log(bookDiv.innerHTML);
			    		console.log("CAOOO SAVO");
			    		bookContainer.append(bookDiv);
			    	}
			    },		 
			    error: function (response, textStatus, errorThrown) {
				   console.error("Failed to create a new book!", response);
			   }
		     });  
	});
	
	$( "#btnDeleteAllBooks" ).click(function() {
		$("#allBooksContainer").empty();

		$.ajax({
				
	        url: "api/v1/book/removeAll",
	        
	        success: function(data, textStatus, response) {
	        	console.log("BROJ KNJIGA: "+data.length);

	        },		 
		    error: function (response, textStatus, errorThrown) {
			   console.error("Failed to create a new book!", response);
		   }
	     });  
});

	
	
	
	
	
	
	// everything is done inside these brackets
});

