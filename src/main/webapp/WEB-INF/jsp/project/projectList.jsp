<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>Home</title>

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
			$(".create-project").show("slow");
		});
		
		$("#cancel-create-req").click(function() {
			$(".create-project").hide("slow");;
		});
		
		$("#list-link").click(function(){
			var listOfItems = $("#item-list");
			
			if(listOfItems.is(":empty")) {
				$.ajax({
				     url:"showAll",
				     type:"GET",
				     success:function(result){
				        listOfItems.html(result);
				     }
				});
				
				$("#list-link").html('<span class="glyphicon glyphicon-chevron-up"></span>');
			} else {
				listOfItems.empty();
				$("#list-link").html('<span class="glyphicon glyphicon-chevron-down"></span>');
			}
		});
	});
</script>

</head>


<body>
	<%@ include file="/WEB-INF/layouts/header.jspf" %>
	<div class="container">
		<div class="collapse navbar-collapse navbar-ex1-collapse column-menu col-md-4 ">
	        <button class="btn btn-default link-frame">New project</button>         
        </div>
        <div class="col-md-2"></div>
        <div class="col-md-6">
        	<div class="create create-project panel panel-default">
        		<div class="panel-heading">
        			<h2 class="panel-title">
        				<i class="glyphicon glyphicon-plus"></i> New project 
        			</h2>
        		</div>
		           <form action="createGeneric" method="POST">
						<input name="project.title" placeholder="Title" type="text" class="form-control">
						<br/>
						<select class="form-control">
							<option value="0" selected="selected">Processo de desenvolvimento</option>
							<option value="1">Scrum</option>
							<option value="2">Processo Unificado</option>
							<option value="3">Generic</option>
						</select>
						<br/>
						<input name="project.description" placeholder="Description" type="text" class="form-control">
						<br/> 
						<input type="submit" value="Add" class="btn btn-success btn-group-justified">
					</form>
	            <br/>
  				<div class="panel-footer">
					<button id="cancel-create-req" class="btn btn-warning">Cancel</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>