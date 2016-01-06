/** 
 * This script controls some functions into home 
 * page of projects.
 * 
 */
$(document).ready(function() {
	$(".link-create-r-modal").click(function() {

		/* Link of page that call an GET method. 
		 * Example: 
		 *	 link - requirement/generic
		 *	 method on Requirement: public Long generic(Long projectID)
		 */
		requirementFormUrl = $(this).attr("href") + "/" +page.projectID;
		
		$.ajax({
		     url: requirementFormUrl,
		     type:"GET",
		     success:function(result) {
		    	 $("#r-form").html(result);
	     }});
	});
});

/**
 * Used by next and previous functions, 
 * get an page through get request.
 */
function paginate() {
	var requirements = $(".requirements");
	
	currentPaginate = page.projectID + "/paginate/" + page.number;
	
	$.ajax({
	     url: currentPaginate,
	     type:"GET",
	     success:function(result){
	    	 requirements.html(result);
     }});
}

/**
 * Get by ajax the next page of requirements
 */
function next() {
	page.number += 1;
	
	paginate();
}

/** 
 * Get by ajax the previous page of requirements
 */
function previous() {
	page.number -= 1;
	
	paginate();
}