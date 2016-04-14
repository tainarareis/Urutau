<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script async="async" src="<c:url value='/js/form-validator.js'/>"></script>

<script async="async" type="text/javascript">
	COLLECTOR.validates({
		'title' : 'useCase.title',
		'projectID' : 'useCase.projectID'
	});
</script>
<div class="modal-content" >
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Use case</h4>
    </div> 
	<div id="useCase" class="requirement-box">
		<form action="requirement/createUseCase" method="POST" class="requirement-form">
			<input name="useCase.projectID" type="hidden" value="${projectID}">
			
			<div class="alert alert-danger form-error" id="title-error" role="alert"></div>
			<input name="useCase.title" class="form-control" placeholder="Title" type="text">
			
			<input name="useCase.description" class="form-control" placeholder="Description" type="text">
			<input name="useCase.fakeActors" class="form-control" placeholder="Separated by ','" type="text">
			<input type="submit" value="Add" class="btn btn-success btn-group-justified submit-create">
		</form>
	</div>						
</div>