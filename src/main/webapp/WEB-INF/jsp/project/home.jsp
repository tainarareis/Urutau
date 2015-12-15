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
	$(document).ready(function(){
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
		
		/**
		
		var requirements = $(".requirements");
		
		showAllUrl = "showAll/${projectID}";
		
		$.ajax({
		     url: showAllUrl,
		     type:"GET",
		     success:function(result){
		    	 requirements.html(result);
	     }});
		
		*/
	});
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
 				<div class="panel-heading">
 		   			<h1 class="panel-title">
 		   				<i class="glyphicon glyphicon-list"></i> List of requirements 
 		   			</h1>
 				</div>
				<div class="panel-body">
					<div id="item-list"></div>
				<div class="panel-body requirements">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Title</th>
								<th>Author</th>
								<th>Creation Date</th>
								<th>Show</th>
								<th>Edit</th>
								<th>Delete</th>
							</tr>
						</thead>
						<tbody>
					  		<c:forEach items="${project.requirements}" var="requirement" >
						  		<tr>
						  			<td>${requirement.title}</td>
						  			<td>
						  				${requirement.author.login}
						  			</td>
						  			<td>
						  				 <fmt:formatDate value="${requirement.dateOfCreation.time}" pattern="dd/MM/yyyy"/>
						  			</td>
						  			<td>
						  			<a href="showRequirement/${requirement.id}/${requirement.title}" title="Show"  
						  				data-toggle="modal" data-target="#myModal-${requirement.id}">
							        	<span class="glyphicon glyphicon-eye-open"></span>
							        </a>
					  					
					  					<div class="modal fade" id="myModal-${requirement.id}"  tabindex="-1" role="dialog">
										    <div class="modal-dialog">
										    
										      <!-- Modal content-->
										      <div class="modal-content">
										        <div class="modal-header">
										          <a href="showRequirement/${requirement.id}/${requirement.title}" title="Show">
										          	<span class="glyphicon glyphicon-eye-open"></span>
										          </a>
										          <h4 class="modal-title">${requirement.title} <br> By ${requirement.author.name} in 
													<fmt:formatDate value="${requirement.dateOfCreation.time}" type="date" dateStyle="short" />
										          </h4>
										        </div>
										        <div class="modal-body">
										          <p>${requirement.description}</p>
										        </div>
										        <div class="modal-footer">
										          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
										        </div>
										      </div>
										      
										    </div>
										  </div>
			
						  			</td>
						  			<td>
						  				<a href="edit/${requirement.id}" title="Edit">
						  					<span class="glyphicon glyphicon-pencil"></span>
						  				</a>
						  			</td>
						  			<td>
						  				<a href="excludeRequirement/${requirement.id}" title="Delete">
						  					<span class="glyphicon glyphicon-remove"></span>
						  				</a>
						  			</td>		  				
						  		</tr>
					  		</c:forEach>
					  	</tbody>
					 </table>
 				</div>
 			</div>
       	</div>
 		</div>
 </div>
 </body>
 </html>
