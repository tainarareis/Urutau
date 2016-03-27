<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script async="async" src="<c:url value='/js/requirement-create.js'/>"></script>

<script async="async" type="text/javascript">
	VALIDATOR.validates({
		title : 'generic.title'
	});
</script>

<div class="modal-content">
	<div class="modal-header">
    	<h4><i class="glyphicon glyphicon-plus"></i> Generic</h4>
    </div> 
	<div class="requirement-box form-group">
		<form action="requirement/createGeneric" method="POST" class="requirement-form">
			<input name="generic.projectID" type="hidden" value="${projectID}">
			<div class="alert alert-danger form-error" id="title-error" role="alert" id="title"></div>
			<input name="generic.title" placeholder="Title" type="text" class="form-control">
			<input name="generic.description" placeholder="Description" type="text" class="form-control" > 
			<button type="submit" class="btn btn-success btn-group-justified submit-create">Add</button>
		</form>
	</div>
</div>