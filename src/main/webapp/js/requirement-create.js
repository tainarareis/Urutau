

// used like data of ajax method
var postData = [];

// loads elements that needs validate
var elementsToValidate = {};

function validates(elements) {
	for(var key in elements) {
		var fieldToValidate = $('input[name="'+elements[key]+'"]');

		if (fieldToValidate.length) {
			elementsToValidate[key] = elements[key];
		} else {
			alert("Wrong set validation, contact administrator");
		}
	}
}

/**
 * set postData with current value of inputs to validate
 * 
 * @param elements have paramName and name of input belonging to him
 */
function mountPostData() {
	// clear postData
	postData = [];
	
	inputOption = JSON.stringify(elementsToValidate);

	$.each($.parseJSON(inputOption), function(paramName, elementName) {
		// get input value by name
		inputToValidate = $('input[name="' + elementName + '"]').val();
		
		// set attribute
		var ajaxParam = {};
		ajaxParam[paramName] = inputToValidate;
		
		// push attribute to postData
		postData.push(JSON.stringify(ajaxParam));
	});
}

/**
 * Throws error messages sented by server-side 
 */
function throwErrorMessages(data){
	// [{'category':value , 'message':value}, ...others]
	$.each($.parseJSON(data), function(properties, values) {
		var category = null;
		$.each(values, function(property, value) {
			if(category == null) {
				category = value;
			} else {
				// render error
				$('#' + category + '-error').show();
	    		$('#' + category + '-error').html(value);
	    		
	    		category = null;
			}
		});
	});
}

$(document).ready(function() {
	
	$(".submit-create").click(function(ev) {
		// cancel submit
		ev.preventDefault();
		
		// fills postData with current values of inputs
		mountPostData();
		
		console.log(postData);
	   	$.ajax({
		     url: "requirement/validate",
		     type: "POST",
		     data: $.parseJSON(postData),
		     // has some error
		     success: function(result) {
		    	var data = JSON.stringify(result);
				
	    		throwErrorMessages(data);
		     },
		     // All is right, submit the form
		     error: function() {
		    	// do something like load div
		    	$(".requirement-form").submit();
		     }
		});
	});
});
