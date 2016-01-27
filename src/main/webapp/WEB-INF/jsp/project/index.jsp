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

</head>

<body>
	
	<%@ include file="/WEB-INF/layouts/header.jspf" %>
	
	<div class="row">	
		<div class="navbar-default sidebar" role="navigation">
			<div class="col-md-3">
	        	<div class="sidebar-nav navbar-collapse">
		        	<ul class="nav" id="side-menu">                         
			            	<li id="projects">
			                	<a href="javascript:;" data-toggle="collapse" data-target="#demo" class="header-option">
			                		Projects
			                	</a>
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
				             	<a href="#" class="header-option" data-toggle="modal" data-target="#create-r-modal">Create Project</a>
				             </li>            
				        </ul>
				    </div>
				</div>
			</div>
		
		<div class="col-md-8">	
   			<div class="modal fade"  tabindex="-1" id="create-r-modal" role="dialog">
				<div class="modal-dialog" id="r-form">
					<div class="modal-content" >
						<div class="modal-header">
					    	<h4><i class="glyphicon glyphicon-plus"></i> Create Project</h4>
					    </div>
					     
						<form action="project/create" method="POST">
							<input name="project.title" placeholder="Title" type="text" class="form-control">
							<br/>
							<select class="form-control" name="project.metodology">
								<c:forEach items="${metodologies}" var="metodology">
									<c:if test="${metodology == 'Generic'}">
										<option value="${metodology}" selected="selected">${metodology}</option>
									</c:if>
									<c:if test="${metodology != 'Generic'}">
										<option value="${metodology}">${metodology}</option>
									</c:if>
								</c:forEach>
							</select>
							<br/>
							<input name="project.description" placeholder="Description" type="text" class="form-control">
							<br/> 
							<input type="submit" id="add-project" class="btn btn-success btn-group-justified" value="Add"/>					
						</form>
						
					</div>
				</div>
			</div>	
		</div>
	</div>
</body>
</html>