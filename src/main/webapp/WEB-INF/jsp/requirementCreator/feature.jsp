<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script async="async" src="<c:url value='/js/create-validator.js'/>"></script>

<script async="async" type="text/javascript">
	VALIDATOR.validates({
		title : 'feature.title'
	});
</script>

<div class="modal-content" >
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Feature</h4>
    </div> 
	<div id="feature" class="requirement-box">
		<form action="requirement/createFeature" method="POST" class="requirement-form">
			<input name="feature.projectID" type="hidden" value="${projectID}">
			
			<div class="alert alert-danger form-error" id="title-error" role="alert"></div>			
			<input name="feature.title" class="form-control" placeholder="Title" type="text">
			
			<input name="feature.description" class="form-control" placeholder="Description" type="text">
			<input name="feature.content" class="form-control" placeholder="Content" type="text">
			<input type="submit" value="Add" class="btn btn-success btn-group-justified submit-create">
		</form>
	</div>
</div>