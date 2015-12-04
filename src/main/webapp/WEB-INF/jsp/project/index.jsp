<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<title>Home</title>

<link href="<c:url value='/css/stylesheet.css'/>" rel="stylesheet">

<link href="<c:url value='/css/sb-admin.css'/>" rel="stylesheet">

<!-- Bootstrap min css -->
<link href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet">

<!-- jQuery -->
<script src="<c:url value='/js/jquery.js'/>"></script>

<!-- Bootstrap Core -->
<script src="<c:url value='/js/bootstrap.min.js'/>"></script>

<script type="text/javascript">
<script type="text/javascript">
	// when page loads
	$(document).ready(function(){
		
		$(".link-frame").click(function() {
			$(".create-project").show("slow");
		});
		
		$("#cancel-create-project").click(function() {
			$(".create-project").hide("slow");;
		});
		
		$("#list-link").click(function(){
			var listOfItems = $("#item-list");
			
			if(listOfItems.is(":empty")) {
				$.ajax({
				     url:"/project/showAll",
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

	<div class="row">	
		<%@ include file="/WEB-INF/layouts/header.jspf" %>
		<div class="navbar-default sidebar" role="navigation">
			<div class="col-md-3">
	        	<div class="sidebar-nav navbar-collapse">
	            	<ul class="nav" id="side-menu">                         
	               	   <li>
	                        <a href="<c:url value="/project/createProject"/>" target="frame-project" class="link-frame">
	                        	Create project
	                        </a>
	                    </li>
	                    <li>
	                    	<a href="" id="list-link">
								Choose a project
							</a>
							<div id="item-list"></div>
	                    </li>	                	                       	   
			     	</ul>
			     </div>
			</div>
		</div>
		<div class="col-md-8">
        	<div class="create create-project panel panel-default">
        		<div class="panel-heading">
        			<h2 class="panel-title">
        				<i class="glyphicon glyphicon-plus"></i> Create Project 
        			</h2>
        		</div>
	            <iframe src="" name="frame-project" width="100%" height="100%"></iframe>
	            <br/>
	            <div class="panel-footer">
					<button id="cancel-create-project" class="btn btn-warning">Cancel</button>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>