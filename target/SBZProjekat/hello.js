$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/api/v1/book/57588c14c14b2fa8c2c7d875"
    }).then(function(data) {
    	
    	
    	
       $('#bookDiv').append(data.name);
    });
    
    
    $.ajax({
        url: "http://localhost:8080/api/v1/book/all"
    }).then(function(data) {
    	  $('#bookDiv').append("IZ NIZA::::::SVI:");
    	
    	for(var i =0; i < data.length; i++) {
    	       $('#bookDiv').append(i + ":" + data[i].name);
    		
    	}

    });
    
    
});
