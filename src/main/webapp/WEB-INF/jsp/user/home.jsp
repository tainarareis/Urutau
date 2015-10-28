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
			$(".create-requirement").show("slow");
		});
		
		$("#cancel-create-req").click(function() {
			$(".create-requirement").hide("slow");;
		});
		
	});
</script>

</head>
<body>
	<!-- Navigation -->
	 <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	 	<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<a class="navbar-brand" href="#">Urutau</a>
			</div>
			<!-- Top Menu Items -->
			<ul class="nav navbar-right top-nav">
				<li class="dropdown">
					<a href="#" id="top-menu-btn" class="dropdown-toggle" data-toggle="dropdown">Sr. ${userManager.userLogged.login}</a>
					<ul id="top-menu-dropdown" class="dropdown-menu">
						<li><a href="#">Perfil</a></li>
						<li><a href="#">Configuration</a></li>
						<li class="divider"></li>
						<li><a href="#"> Log Out</a></li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
		
	<div class="container">
		<div class="collapse navbar-collapse navbar-ex1-collapse column-menu col-md-4 ">
	        <ul class="list-unstyled pull-left">                    
	            <li>
	                <a href="javascript:;" data-toggle="collapse" data-target="#demo" class="header-option">Add an requirement</a>
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
	             	<a href="#" class="header-option">Other option</a>
	             </li>            
	        </ul>
        </div>
        <div class="col-md-2"></div>
        <div class="col-md-6">
        	<div class="create-requirement">
	            <iframe src="" name="frame-req" width="100%" height="100%"></iframe>
	            <br/>        
				<button id="cancel-create-req" class="btn btn-warning">Cancel</button>
			</div>
     		<h1>List of requirements</h1>
      	</div>
	</div>
</body>
</html>
