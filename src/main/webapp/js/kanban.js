/**
 * Used into kanban paths
 * 
 * Drag drop is based on :
 * http://www.w3schools.com/html/tryit.asp?filename=tryhtml5_draganddrop
 */
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("requirement", ev.target.id);
}

function drop(ev, layerID) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("requirement");
    var requirement = $("#".concat(data))[0];

    var requirementID = requirement.id.replace(/[^0-9]/g,'');
    
    ev.target.appendChild(requirement);
    
	$.ajax({
	     type:"POST",
	     url: "/Urutau/kanban/move",
	     data: {requirementID: requirementID, layerID: layerID},
	     dataType: "JSON"
	});
}
$(document).ready(function(){

	$(document).click(function(event) {
		$(".requirement").css('borderWidth', '1px');
				
		if ($(event.target).hasClass('requirement')){
			$(event.target).css('borderWidth', '3px');
		}
	});
	
	$(".requirement").click(function() {
		$(this).css('borderWidth', '3px');	
	});
});
