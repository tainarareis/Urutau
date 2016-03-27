<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script async="async" src="<c:url value='/js/create-validator.js'/>"></script>

<script async="async" type="text/javascript">
	VALIDATOR.validates({
		title : 'epic.title'
	});
</script>

<div class="modal-content" >
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Epic</h4>
    </div> 
	<div id="epic" class="requirement-box">
		<form action="requirement/createEpic" method="POST" class="requirement-form">
			<input name="epic.projectID" type="hidden" value="${projectID}">
			
			<div class="alert alert-danger form-error" id="title-error" role="alert"></div>
			<input name="epic.title" class="form-control" placeholder="Title" type="text">
			
			<input name="epic.description" class="form-control" placeholder="Description" type="text">
			<input name="epic.content" class="form-control" placeholder="Content" type="text">
			<input type="submit" value="Add" class="btn btn-success btn-group-justified submit-create">
		</form>
	</div>
</div>