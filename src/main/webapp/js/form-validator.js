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
var COLLECTOR = (function() {

  // loads elements that needs validate in JSON
  var toValidate = {};
	
  toValidate.elements = {};
	
  toValidate.data = [];
	
  
  // Save fields to verify
  toValidate.validates = function(elements) {
    console.log(elements);
	
    toValidate.elements = elements;
  }
	
  // Update data with inputs to validates
  toValidate.update = function() {
		
    toValidate.data = [];
		
	var fields = toValidate.elements;
		
	for(var key in fields) {
	  var fieldToValidate = $('input[name="'+fields[key]+'"]');
	    
	  var parameterValue = fieldToValidate.val();
	    if (fieldToValidate.length) {
		  var data = {};
		  data[key] = parameterValue;
		  toValidate.data.push(data);
		} else {
		  alert("Invalid element to validation! Contact administrator...");
		  window.location.reload(true);
		}
	}
	
	console.log(JSON.stringify(toValidate.data));
  };
	
  // return data in JSON format
  toValidate.getJSON = function() {
    var ajaxData = {};
		
	$.each(toValidate.data, function(index, element) {
		$.each(element, function(param, value){
			ajaxData[param] = value;
		})
	});
		
	console.log(ajaxData);
		
	return ajaxData;
  };
	
  return {
    getDataInJSON: toValidate.getJSON,
	update : toValidate.update,
	validates: toValidate.validates	
  };
})(); 

/**
 * Throws error messages sented by server-side 
 */
function throwErrorMessages(data) {
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
		
	COLLECTOR.update();
		
	$.ajax({
		url: "requirement/validate",
		type: "POST",
		data: COLLECTOR.getDataInJSON(),
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