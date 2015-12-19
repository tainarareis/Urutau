<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>${project.title}</title>

<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">

<!-- Bootstrap min css -->
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value='/js/jquery.js'/>"></script>

<!-- Bootstrap Core -->
<script src="<c:url value='/js/bootstrap.min.js'/>"></script>

<script type="text/javascript">
	// when page load
	$(document).ready(function() {
		$(".link-frame").click(function() {
			$(".create-requirement").show("slow");
		});
		
		$("#cancel-create-req").click(function() {
			$(".create-requirement").hide("slow");
		});
		
		$(".link-frame").click(function(event) {
			// Cancel redirect
			event.preventDefault();
			
			/* Link of page that call an GET method. 
			 * Example: 
			 *	 link - requirement/generic
			 *	 method on Requirement: public Long generic(Long projectID)
			 */
			requirementFormUrl = $(this).attr("href")+'/${project.projectID}';

			$.ajax({
			     url: requirementFormUrl,
			     type:"GET",
			     success:function(result){
			    	 $(".create-frame").html(result);
			    	 
		     }});
		});
	});
	
	var page = 0;
	
	window.onload = function(event) {
		paginate();
	}
	
	function paginate() {
		var requirements = $(".requirements");
		
		currentPaginate = "${project.projectID}/paginate/" + page;
		
		$.ajax({
		     url: currentPaginate,
		     type:"GET",
		     success:function(result){
		    	 requirements.html(result);
	     }});
	}
	
	function next() {
		page += 1;
		
		paginate();
	}
	
	function previous() {
		page -= 1;
		
		paginate();
	}
</script>

</head>
<body>
	<%@ include file="/WEB-INF/layouts/header.jspf" %>
	<div class="row">	
		<div class="navbar-default sidebar" role="navigation">
			<div class="col-md-3">
	        	<div class="sidebar-nav navbar-collapse">
	            	<ul class="nav" id="side-menu">                         
		            	<li>
		                	<a href="javascript:;" data-toggle="collapse" data-target="#demo" class="header-option">Create requirement</a>
		                	<ul id="demo" class="collapse list-unstyled suboption">
		                    <li>
		                        <a href="<c:url value="/requirement/generic"/>" class="link-frame">
		                        	Generic Requirement
		                        </a>
		                    </li>
		                    <li>
		                        <a href="<c:url value="/requirement/storie"/>" class="link-frame">
		                        	User Story
		                        </a>
		                    </li>
		                    <li>
		                        <a href="<c:url value="/requirement/feature"/>" class="link-frame">
		                        	Feature
		                        </a>
		                    </li>
		                    <li>
		                       <a href="<c:url value="/requirement/epic"/>" class="link-frame">
		                       		Epic
		                       	</a>
		                    </li>
		                    <li>
		                        <a href="<c:url value="/requirement/useCase"/>" class="link-frame">
		                        	Use Case
		                        </a>
		                    </li>                                                      
		                </ul>
			             </li>
			             <li>
			             	<a href="#" class="header-option">More options</a>
			             </li>
			             <li>
							<a href="#" class="header-option">Settings</a>
			             </li>
			             <li>
			             	<a href="#" class="header-option">Activity</a>
			             </li>            
			        </ul>
		        </div>
	       	</div>    
 		</div>
 		
        <div class="col-md-8">
         	<div class="create create-requirement panel panel-default">
         		<div class="panel-heading">
         			<h2 class="panel-title">
         				<i class="glyphicon glyphicon-plus"></i> Create Requirement 
         			</h2>
         		</div>
 	            
 	          	<div class="create-frame">
 	          	
 	          	</div>
 	            
 	            <br/>
   				<div class="panel-footer">
 					<button id="cancel-create-req" class="btn btn-warning">Cancel</button>
 				</div>
 			</div>
 			<div class="panel panel-default">
 				<div class="panel-body">
				<div class="panel-body requirements">
					
				</div>
 				</div>
       		</div>
 		</div>
 </div>
 </body>
 </html>
