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
			                	<a href="javascript:;" data-toggle="collapse" data-target="#demo" class="header-option">Projects</a>
			                	<ul id="demo" class="collapse list-unstyled suboption">
			                 		<c:forEach items="${projects}" var="project">
					                    <li>
					                        <a href="${project.projectID}-${project.title}" target="frame-req" class="link-frame">
					                        	${project.title}
					                        </a>
					                    </li>
				                    </c:forEach>                                                       
			                	</ul>
				             </li>
				             <li>
				             	<a href="#" class="header-option">Create Project</a>
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