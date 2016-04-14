<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script async="async" src="<c:url value='/js/form-validator.js'/>"></script>

<script async="async" type="text/javascript">
	COLLECTOR.validates({
		'title' : 'storie.title',
		'projectID' : 'storie.projectID'
	});
</script>

<div class="modal-content" >
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Storie</h4>
    </div> 
	<div class="requirement-box">
		<form action="requirement/createUserStory" method="POST" class="requirement-form">
			<input name="storie.projectID" type="hidden" value="${projectID}">
			
			<div class="alert alert-danger form-error" id="title-error" role="alert"></div>			
			<input type="text" name="storie.title" placeholder="Title" class="form-control">
			
			<label>Tell the Story:</label>
			<textarea name="storie.history" class="form-control">
			
			</textarea>
			<input type="text" name="acceptanceCriteria.content" class="form-control" placeholder="Acceptance criteria">
			<button type="submit" class="btn btn-success btn-group-justified submit-create">Add</button>
		</form>
	</div>
</div>