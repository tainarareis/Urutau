/** 
 * This global variable sets a map to ajax method 
 * use the marked inputs to validation. 
 * Basically we have a server-side validate method like:
 * 
 * public void validate(String property) {
 * 
 * And a view with:
 * 
 * <input type="text" name="object.property">
 * 
 * So is needed pass this input value like 'property' to ajax method:
 * 
 * data : {name : 'some entry'};
 * 
 */
var VALIDATOR = (function() {

	// used by ajax method
	var data = [];

	// loads elements that needs validate
	var elementsToValidate = {};
	
	// Named by 'validates', this method marks inputs 
	// to be validated and your keys
	elementsToValidate.add = function(elements) {
		for(var key in elements) {
			var fieldToValidate = $('input[name="'+elements[key]+'"]');
			
			if (fieldToValidate.length) {
				elementsToValidate[key] = elements[key];
			} else {
				alert("Invalid element to validation! Contact administrator...");
				window.location.reload(true);
			}
		}
	};

	// Generates the needed association between input and value, 
	// to be used by ajax
	data.generateMap = function() {
		
		data = [];
		
		var inputs = JSON.stringify(elementsToValidate);

		$.each($.parseJSON(inputs), function(paramName, elementName) {
			// get input value by name
			var toValidate = $('input[name="' + elementName + '"]').val();
			
			// set attribute
			var ajaxParam = {};
			ajaxParam[paramName] = toValidate;
			
			// push attribute to postData
			data.push(JSON.stringify(ajaxParam));
		});
	}
	
	// return data in JSON format
	data.getJSON = function() {
		return $.parseJSON(data);
	};
	
	
	return {
		mapInputs	: data.generateMap,
		getJSON		: data.getJSON,
		validates	: elementsToValidate.add	
	};
})(); 


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
		VALIDATOR.mapInputs();
		
	   	$.ajax({
		     url: "requirement/validate",
		     type: "POST",
		     data: VALIDATOR.getJSON(),
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