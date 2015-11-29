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
	// when page load
	$(document).ready(function(){
		
		$(".link-frame").click(function() {
			$(".create-requirement").show("slow");
		});
		
		$("#cancel-create-req").click(function() {
			$(".create-requirement").hide("slow");;
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
		<div class="row">	
		<%@ include file="/WEB-INF/layouts/header.jspf" %>
		<div class="navbar-default sidebar role="navigation">
		<div class="col-md-3">
        	<div class="sidebar-nav navbar-collapse">
            	<ul class="nav" id="side-menu">                         
	            	<li>
	                	<a href="javascript:;" data-toggle="collapse" data-target="#demo" class="header-option">Create requirement</a>
	                	<ul id="demo" class="collapse list-unstyled suboption">
	                    <li>
	                        <a href="<c:url value="/requirement/generic"/>" target="frame-req" class="link-frame">
	                        	Generic Requirement
	                        </a>
	                    </li>
	                    <li>
	                        <a href="<c:url value="/requirement/storie"/>" target="frame-req" class="link-frame">
	                        	User Story
	                        </a>
	                    </li>
	                    <li>
	                        <a href="<c:url value="/requirement/feature"/>" target="frame-req" class="link-frame">
	                        	Feature
	                        </a>
	                    </li>
	                    <li>
	                       <a href="<c:url value="/requirement/epic"/>" target="frame-req" class="link-frame">
	                       		Epic
	                       	</a>
	                    </li>
	                    <li>
	                        <a href="<c:url value="/requirement/useCase"/>" target="frame-req" class="link-frame">
	                        	Use Case
	                        </a>
	                    </li>                                                      
	                </ul>
	             </li>
	             <li>
	             	<a href="#" class="header-option">More options</a>
	             </li>            
	        </ul>
        </div>
       </div>    
 	</nav>
 	</div>
        <div class="col-md-8">
        	<div class="create create-requirement panel panel-default">
        		<div class="panel-heading">
        			<h2 class="panel-title">
        				<i class="glyphicon glyphicon-plus"></i> Create Requirement 
        			</h2>
        		</div>
	            <iframe src="" name="frame-req" width="100%" height="100%"></iframe>
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
				<a href="#" id="list-link">
					<span class="glyphicon glyphicon-chevron-down"></span>
				</a>
				<div id="item-list"></div>
			</div>
      	</div>
      	</div>
</body>
</html>
