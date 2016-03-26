<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">

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
				$('#' + category + '-error').show();
	    		$('#' + category + '-error').html(value);
	    		category = null;
			}
		});
	});
}

$(document).ready(function() {
	
	$(".submit-create").click(function(ev) {
		
	   	ev.preventDefault();
		
	   	$.ajax({
		     url: "requirement/validate",
		     type: "POST",
		     data: {title: $("#title").val()},
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

</script>
<div class="modal-content" >
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Generic</h4>
    </div> 
	<div class="requirement-box form-group">
		<form action="requirement/createGeneric" method="POST" class="requirement-form">
			<input name="generic.projectID" type="hidden" value="${projectID}">
			<div class="alert alert-danger form-error" id="title-error" role="alert"></div>
			<input name="generic.title" placeholder="Title" type="text" class="form-control" id="title">
			<input name="generic.description" placeholder="Description" type="text" class="form-control" > 
			<button type="submit" class="btn btn-success btn-group-justified submit-create">Add</button>
		</form>
	</div>
</div>